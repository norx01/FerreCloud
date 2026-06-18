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

    // 1. Consultar todos los registros del inventario
    @GetMapping
    public List<producto> listar() {
        return service.listar();
    }

    // Consultar un registro específico por su ID (String para MongoDB)
    @GetMapping("/{id}")
    public producto buscar(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    // 2. Registrar en el inventario
    @PostMapping
    public producto guardar(@RequestBody producto producto) {
        return service.guardar(producto);
    }

    // 3. Actualizar un registro del inventario
    @PutMapping("/{id}")
    public producto actualizar(@PathVariable String id, @RequestBody producto producto) {
        // Aseguramos que el ID del objeto coincida con el de la URL antes de guardar
        producto.setId(id);
        return service.guardar(producto);
    }

    // 4. Eliminar un registro
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminar(id);
    }

    // 5. Notificación de stock bajo
    @GetMapping("/alertas-stock")
    public List<producto> obtenerAlertasStock() {
        return service.obtenerProductosConStockBajo();
    }
}