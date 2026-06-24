package com.ferrecloud.ferrecloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usuarios")
public class Usuarios {

    @Id
    private String id;

    private String nombre;
    private String documento;
    private String telefono;
    private String direccion;
    private String correoElectronico;
    private String cargo;
    private Double salario;
    private String password;
    private Rol rol;

    public enum Rol {
        EMPLEADO,
        ADMINISTRADOR
    }
}