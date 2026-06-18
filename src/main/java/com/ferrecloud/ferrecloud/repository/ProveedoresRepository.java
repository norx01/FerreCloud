package com.ferrecloud.ferrecloud.repository;

import com.ferrecloud.ferrecloud.model.Proveedores;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ProveedoresRepository extends MongoRepository<Proveedores, Integer> {

    Optional<Proveedores> findTopByOrderByIdDesc();
}