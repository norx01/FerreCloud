package com.ferrecloud.ferrecloud.service;

import com.ferrecloud.ferrecloud.dto.VentaDTO;
import com.ferrecloud.ferrecloud.model.Clientes;
import com.ferrecloud.ferrecloud.model.ItemOrden;
import com.ferrecloud.ferrecloud.model.Venta;
import com.ferrecloud.ferrecloud.model.producto;
import com.ferrecloud.ferrecloud.repository.ClientesRepository;
import com.ferrecloud.ferrecloud.repository.InventarioRepository;
import com.ferrecloud.ferrecloud.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.ferrecloud.ferrecloud.enums.EstadoOrden;
import com.ferrecloud.ferrecloud.model.OrdenCompra;
import com.ferrecloud.ferrecloud.repository.OrdenCompraRepository;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final InventarioRepository inventarioRepository;
    private final ClientesRepository clientesRepository;
    private final OrdenCompraRepository ordenRepository;

    public VentaService(VentaRepository ventaRepository,
                        InventarioRepository inventarioRepository,
                        ClientesRepository clientesRepository,
                        OrdenCompraRepository ordenRepository) {
        this.ventaRepository = ventaRepository;
        this.inventarioRepository = inventarioRepository;
        this.clientesRepository = clientesRepository;
        this.ordenRepository = ordenRepository;
    }

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(String id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));
    }

    public Venta registrarDesdeOrden(String ordenId) {
        // 1. Buscar la orden
        OrdenCompra orden = ordenRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada: " + ordenId));

        if (orden.getEstado() != EstadoOrden.PENDIENTE) {
            throw new RuntimeException("La orden ya fue procesada.");
        }

        // 2. Verificar y descontar stock de cada producto
        List<ItemOrden> itemsActualizados = orden.getProductos().stream().map(item -> {
            producto prod = inventarioRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + item.getProductoId()));

            if (prod.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + prod.getNombre()
                        + " (disponible: " + prod.getStock() + ")");
            }

            prod.setStock(prod.getStock() - item.getCantidad());
            inventarioRepository.save(prod);

            return item; // el item ya tiene nombre, precio y subtotal desde la orden
        }).collect(Collectors.toList());

        // 3. Crear la venta a partir de la orden
        Venta venta = new Venta();
        venta.setClienteId(orden.getClienteId());
        venta.setClienteNombre(orden.getClienteNombre());
        venta.setEmpleadoId(orden.getVendedorId());
        venta.setEmpleadoNombre(orden.getVendedorNombre());
        venta.setFecha(LocalDateTime.now());
        venta.setProductos(itemsActualizados);
        venta.setTotal(orden.getTotal());
        venta.setEstado("Completa");

        // 4. Marcar la orden como PAGADA
        orden.setEstado(EstadoOrden.PAGADA);
        ordenRepository.save(orden);

        return ventaRepository.save(venta);
    }

    public void eliminar(String id) {
        ventaRepository.deleteById(id);
    }
}