package com.ferrecloud.ferrecloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clientes")
public class Clientes
{

    @Id
    private String id;

    private String nombre;
    private String documento;
    private String telefono;
    private String direccion;
    private String correoElectronico;
}