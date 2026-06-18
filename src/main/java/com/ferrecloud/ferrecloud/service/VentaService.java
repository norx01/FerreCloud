package com.ferrecloud.ferrecloud.service;

import com.ferrecloud.ferrecloud.dto.ItemOrdenDTO;
import com.ferrecloud.ferrecloud.dto.VentaDTO;
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

    public Venta registrar(VentaDTO dto) {
        // 1. Verificar stock y descontarlo
        List<ItemOrden> items = dto.getProductos().stream().map(d -> {
            producto prod = inventarioRepository.findById(d.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + d.getProductoId()));

            if (prod.getStock() < d.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + prod.getNombre());
            }

            // Descontar stock
            prod.setStock(prod.getStock() - d.getCantidad());
            inventarioRepository.save(prod);

            BigDecimal subtotal = d.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(d.getCantidad()));
            return new ItemOrden(prod.getId(), prod.getNombre(),
                    d.getCantidad(), d.getPrecioUnitario(), subtotal);
        }).collect(Collectors.toList());

        // 2. Calcular total
        BigDecimal total = items.stream()
                .map(ItemOrden::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. Guardar venta
        Venta venta = new Venta();
        venta.setClienteId(dto.getClienteId());
        venta.setEmpleadoId(dto.getEmpleadoId());
        venta.setFecha(LocalDateTime.now());
        venta.setProductos(items);
        venta.setTotal(total);

        return ventaRepository.save(venta);
    }
}