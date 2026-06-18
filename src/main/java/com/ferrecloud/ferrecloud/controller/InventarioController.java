package com.ferrecloud.ferrecloud.controller;

import com.ferrecloud.ferrecloud.model.producto;
import com.ferrecloud.ferrecloud.service.InventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<producto> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public producto buscar(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public producto guardar(@RequestBody producto producto) {
        return service.guardar(producto);
    }

    @PutMapping("/{id}")
    public producto actualizar(@PathVariable String id, @RequestBody producto producto) {
        producto.setId(id);
        return service.guardar(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminar(id);
    }

    @GetMapping("/alertas-stock")
    public List<producto> obtenerAlertasStock() {
        return service.obtenerProductosConStockBajo();
    }
}