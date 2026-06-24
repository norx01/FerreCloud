package com.ferrecloud.ferrecloud.repository;

import com.ferrecloud.ferrecloud.model.Venta;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends MongoRepository<Venta, String> {
    List<Venta> findByClienteId(String clienteId);
    List<Venta> findByEmpleadoId(String empleadoId);
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}
