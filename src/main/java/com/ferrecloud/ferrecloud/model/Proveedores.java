package com.ferrecloud.ferrecloud.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "proveedores")
public class Proveedores
{
    @Id
    private String id;

    private String nombre;
    private String direccion;
    private String telefono;
    private String productosSuministrados;

}
