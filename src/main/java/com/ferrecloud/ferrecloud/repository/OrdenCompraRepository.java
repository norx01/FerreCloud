package com.ferrecloud.ferrecloud.repository;

import com.ferrecloud.ferrecloud.enums.EstadoOrden;
import com.ferrecloud.ferrecloud.model.OrdenCompra;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrdenCompraRepository extends MongoRepository<OrdenCompra, String> {

    List<OrdenCompra> findByClienteId(String clienteId);

    List<OrdenCompra> findByVendedorId(String vendedorId);

    List<OrdenCompra> findByEstado(EstadoOrden estado);
}