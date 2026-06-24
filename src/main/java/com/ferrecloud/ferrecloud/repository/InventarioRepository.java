package com.ferrecloud.ferrecloud.repository;

import com.ferrecloud.ferrecloud.model.producto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends MongoRepository<producto, String> {

//stock minimo para mongo db, no tocar tambien gracias :)
    @Query("{ $expr: { $lte: [ '$stock', '$stockMinimo' ] } }")
    List<producto> findByStockBajo();

}