package com.ferrecloud.ferrecloud.repository;

import com.ferrecloud.ferrecloud.model.producto;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends MongoRepository<producto, Long> {


}