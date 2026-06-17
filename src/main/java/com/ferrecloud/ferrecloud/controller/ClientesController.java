package com.ferrecloud.ferrecloud.controller;


import com.ferrecloud.ferrecloud.model.Clientes;
import com.ferrecloud.ferrecloud.service.ClientesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

    private final ClientesService service;

    public ClientesController(ClientesService service) {
        this.service = service;
    }

    @GetMapping
    public List<Clientes> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Clientes buscar(@PathVariable String id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Clientes guardar(@RequestBody Clientes cliente) {
        return service.guardar(cliente);
    }

    @PutMapping("/{id}")
    public Clientes actualizar(@PathVariable String id,
                               @RequestBody Clientes cliente) {

        cliente.setId(id);
        return service.guardar(cliente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminar(id);
    }
}