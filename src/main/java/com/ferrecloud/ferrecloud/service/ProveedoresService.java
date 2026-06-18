package com.ferrecloud.ferrecloud.service;

import com.ferrecloud.ferrecloud.model.Clientes;
import com.ferrecloud.ferrecloud.model.Proveedores;
import com.ferrecloud.ferrecloud.repository.ClientesRepository;
import com.ferrecloud.ferrecloud.repository.ProveedoresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedoresService
{
    private final ProveedoresRepository repository;

    public ProveedoresService(ProveedoresRepository repository) {
        this.repository = repository;
    }

    public List<Proveedores> listar() {
        return repository.findAll();
    }

    public  Proveedores guardar(Proveedores proveedores) {
        return repository.save(proveedores);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }








}
