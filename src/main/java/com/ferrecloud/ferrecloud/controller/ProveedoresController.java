package com.ferrecloud.ferrecloud.controller;

import com.ferrecloud.ferrecloud.model.Proveedores;
import com.ferrecloud.ferrecloud.service.ProveedoresService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedoresController {

    private final ProveedoresService service;

    public ProveedoresController(ProveedoresService service) {
        this.service = service;
    }


    @GetMapping
    public List<Proveedores> listar() {
        return service.listar();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Proveedores> buscarPorId(@PathVariable Integer id) {
        Proveedores proveedor = service.buscarPorId(id);
        if (proveedor != null) {
            return ResponseEntity.ok(proveedor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Proveedores guardar(@RequestBody Proveedores proveedor) {
        return service.guardar(proveedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.ok("Proveedor eliminado correctamente.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}