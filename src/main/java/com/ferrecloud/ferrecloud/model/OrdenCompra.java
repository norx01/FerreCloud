package com.ferrecloud.ferrecloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ordenes_compra")
public class OrdenCompra {

    @Id
    private String id;

    @Indexed
    private String clienteId;
    private String clienteNombre;

    @Indexed
    private String vendedorId;
    private String vendedorNombre;

    private LocalDateTime fecha;
    private EstadoOrden estado;
    private List<ItemOrden> productos;
    private BigDecimal total;
}