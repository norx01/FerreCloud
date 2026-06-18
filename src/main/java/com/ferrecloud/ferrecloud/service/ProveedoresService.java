package com.ferrecloud.ferrecloud.service;


import com.ferrecloud.ferrecloud.model.Proveedores;
import com.ferrecloud.ferrecloud.repository.ProveedoresRepository;
import com.ferrecloud.ferrecloud.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedoresService
{
    private final ProveedoresRepository repository;
    private final InventarioRepository inventarioRepository;

    public ProveedoresService(ProveedoresRepository repository, InventarioRepository inventarioRepository) {
        this.repository = repository;
        this.inventarioRepository = inventarioRepository;
    }

    public List<Proveedores> listar() {
        return repository.findAll();
    }

    public Proveedores guardar(Proveedores proveedores) {
        return repository.save(proveedores);
    }

    public Proveedores buscarPorId(String id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(String id) {
        Proveedores proveedor = repository.findById(id).orElse(null);

        if (proveedor != null) {
            boolean tieneProductosActivos = inventarioRepository.findAll().stream()
                    .anyMatch(prod -> proveedor.getNombre().equals(prod.getProveedor()) && prod.getStock() > 0);

            if (tieneProductosActivos) {
                throw new IllegalStateException("Restricción: No se puede eliminar el proveedor porque tiene productos activos con stock en el inventario.");
            }
        }

        repository.deleteById(id);
    }
    }



