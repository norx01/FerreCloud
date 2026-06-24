package com.ferrecloud.ferrecloud.repository;

import com.ferrecloud.ferrecloud.model.Usuarios;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuariosRepository extends MongoRepository<Usuarios, String> {
    Optional<Usuarios> findByDocumento(String documento);
}