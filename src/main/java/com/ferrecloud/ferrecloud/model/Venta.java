package com.ferrecloud.ferrecloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "ventas")
public class Venta {
    @Id
    private String id;
    private String clienteId;
    private String clienteNombre;
    private String empleadoId;
    private String empleadoNombre;
    private LocalDateTime fecha;
    private List<ItemOrden> productos;
    private BigDecimal total;
    private String estado; // "Pendiente", "Completa", "Cancelada"
}
