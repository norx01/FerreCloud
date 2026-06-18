package com.ferrecloud.ferrecloud.service;

import com.ferrecloud.ferrecloud.model.producto;
import com.ferrecloud.ferrecloud.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    private final InventarioRepository repository;

    public InventarioService(InventarioRepository repository) {
        this.repository = repository;
    }

    public List<producto> listar() {
        return repository.findAll();
    }


    public producto buscarPorId(String id) {
        return repository.findById(id).orElse(null);
    }

    public producto guardar(producto producto) {
        return repository.save(producto);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }

}