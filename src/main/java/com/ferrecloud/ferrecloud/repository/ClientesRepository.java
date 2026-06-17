package com.ferrecloud.ferrecloud.repository;

import com.ferrecloud.ferrecloud.model.Clientes;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientesRepository extends MongoRepository<Clientes, String>
{

}
