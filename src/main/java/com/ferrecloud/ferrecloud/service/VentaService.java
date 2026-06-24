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

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final InventarioRepository inventarioRepository;
    private final ClientesRepository clientesRepository;

    public VentaService(VentaRepository ventaRepository,
                        InventarioRepository inventarioRepository,
                        ClientesRepository clientesRepository) {
        this.ventaRepository = ventaRepository;
        this.inventarioRepository = inventarioRepository;
        this.clientesRepository = clientesRepository;
    }

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(String id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada: " + id));
    }

    public Venta registrar(VentaDTO dto) {
        // 1. Verificar que el cliente existe y traer su nombre
        Clientes cliente = clientesRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + dto.getClienteId()));

        // 2. Verificar stock, descontarlo y armar items
        List<ItemOrden> items = dto.getProductos().stream().map(d -> {
            producto prod = inventarioRepository.findById(d.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + d.getProductoId()));

            if (prod.getStock() < d.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + prod.getNombre()
                        + " (disponible: " + prod.getStock() + ")");
            }

            // Descontar stock
            prod.setStock(prod.getStock() - d.getCantidad());
            inventarioRepository.save(prod);

            // Precio siempre viene de la BD, no del cliente
            BigDecimal precioUnitario = BigDecimal.valueOf(prod.getPrecio());
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(d.getCantidad()));

            return new ItemOrden(prod.getId(), prod.getNombre(),
                    d.getCantidad(), precioUnitario, subtotal);
        }).collect(Collectors.toList());

        // 3. Calcular total
        BigDecimal total = items.stream()
                .map(ItemOrden::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Construir y guardar la venta con todos los campos
        Venta venta = new Venta();
        venta.setClienteId(cliente.getId());
        venta.setClienteNombre(cliente.getNombre());
        venta.setEmpleadoId(dto.getEmpleadoId());
        venta.setEmpleadoNombre(dto.getEmpleadoId()); // placeholder hasta tener módulo Usuarios
        venta.setFecha(LocalDateTime.now());
        venta.setProductos(items);
        venta.setTotal(total);
        venta.setEstado("Completa");

        return ventaRepository.save(venta);
    }

    public void eliminar(String id) {
        ventaRepository.deleteById(id);
    }
}