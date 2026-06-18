package com.ferrecloud.ferrecloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "producto")
public class producto {

    @Id
    private String id;
    private String nombre;
    private String categoria;
    private Double precio;
    private String proveedor;
    private int Stock;
    private int StockMinimo;

}