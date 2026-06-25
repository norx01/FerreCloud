package com.ferrecloud.ferrecloud.service;

import com.ferrecloud.ferrecloud.dto.ItemOrdenDTO;
import com.ferrecloud.ferrecloud.dto.OrdenCompraDTO;
import com.ferrecloud.ferrecloud.model.Clientes;
import com.ferrecloud.ferrecloud.enums.EstadoOrden;
import com.ferrecloud.ferrecloud.model.ItemOrden;
import com.ferrecloud.ferrecloud.model.OrdenCompra;
import com.ferrecloud.ferrecloud.repository.ClientesRepository;
import com.ferrecloud.ferrecloud.repository.OrdenCompraRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenCompraService {

    private final OrdenCompraRepository repository;
    private final ClientesRepository clientesRepository;

    public OrdenCompraService(OrdenCompraRepository repository,
                              ClientesRepository clientesRepository) {
        this.repository = repository;
        this.clientesRepository = clientesRepository;
    }

    public List<OrdenCompra> listar() {
        return repository.findAll();
    }

    public OrdenCompra buscarPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada: " + id));
    }

    public List<OrdenCompra> listarPorCliente(String clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public List<OrdenCompra> listarPorVendedor(String vendedorId) {
        return repository.findByVendedorId(vendedorId);
    }

    public OrdenCompra crear(OrdenCompraDTO dto) {
        Clientes cliente = clientesRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado: " + dto.getClienteId()));

        List<ItemOrden> items = mapearItems(dto.getProductos());
        BigDecimal total = calcularTotal(items);

        OrdenCompra orden = new OrdenCompra();
        orden.setClienteId(cliente.getId());
        orden.setClienteNombre(cliente.getNombre());
        orden.setVendedorId(dto.getVendedorId());
        orden.setVendedorNombre(dto.getVendedorId());
        orden.setFecha(LocalDateTime.now());
        orden.setEstado(EstadoOrden.PENDIENTE);
        orden.setProductos(items);
        orden.setTotal(total);

        return repository.save(orden);
    }

    public OrdenCompra cambiarEstado(String id, EstadoOrden nuevoEstado) {
        OrdenCompra orden = buscarPorId(id);
        orden.setEstado(nuevoEstado);
        return repository.save(orden);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }

    private List<ItemOrden> mapearItems(List<ItemOrdenDTO> dtos) {
        return dtos.stream().map(d -> {
            BigDecimal subtotal = d.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(d.getCantidad()));
            return new ItemOrden(
                    d.getProductoId(),
                    d.getNombreProducto(),
                    d.getCantidad(),
                    d.getPrecioUnitario(),
                    subtotal
            );
        }).collect(Collectors.toList());
    }

    private BigDecimal calcularTotal(List<ItemOrden> items) {
        return items.stream()
                .map(ItemOrden::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<OrdenCompra> listarPorEstado(EstadoOrden estado) {
        return repository.findByEstado(estado);
    }
}