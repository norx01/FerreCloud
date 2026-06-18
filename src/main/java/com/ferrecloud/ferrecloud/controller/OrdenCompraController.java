package com.ferrecloud.ferrecloud.controller;

import com.ferrecloud.ferrecloud.dto.OrdenCompraDTO;
import com.ferrecloud.ferrecloud.model.EstadoOrden;
import com.ferrecloud.ferrecloud.model.OrdenCompra;
import com.ferrecloud.ferrecloud.service.OrdenCompraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenCompraController {

    private final OrdenCompraService service;

    public OrdenCompraController(OrdenCompraService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrdenCompra> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public OrdenCompra buscar(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<OrdenCompra> porCliente(@PathVariable String clienteId) {
        return service.listarPorCliente(clienteId);
    }

    @GetMapping("/vendedor/{vendedorId}")
    public List<OrdenCompra> porVendedor(@PathVariable String vendedorId) {
        return service.listarPorVendedor(vendedorId);
    }

    @PostMapping
    public ResponseEntity<OrdenCompra> crear(@Valid @RequestBody OrdenCompraDTO dto) {
        return ResponseEntity.ok(service.crear(dto));
    }

    @PutMapping("/{id}/estado")
    public OrdenCompra cambiarEstado(@PathVariable String id, @RequestParam EstadoOrden estado) {
        return service.cambiarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminar(id);
    }
}