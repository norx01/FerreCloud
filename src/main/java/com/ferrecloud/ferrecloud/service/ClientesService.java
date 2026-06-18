package com.ferrecloud.ferrecloud.service;


import com.ferrecloud.ferrecloud.model.Clientes;
import com.ferrecloud.ferrecloud.repository.ClientesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesService {

    private final ClientesRepository repository;

    public ClientesService(ClientesRepository repository) {
        this.repository = repository;
    }

    public List<Clientes> listar() {
        return repository.findAll();
    }

    public Clientes guardar(Clientes cliente) {
        if (cliente.getId() != null && cliente.getId().isBlank()) {
            cliente.setId(null);
        }
        return repository.save(cliente);
    }

    public Clientes buscarPorId(String id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }
}