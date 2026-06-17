package com.ferrecloud.ferrecloud.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientesDTO
{

    @NotBlank
    private String nombre;

    @NotBlank
    private String documento;

    @NotBlank
    private String telefono;

    @NotBlank
    private String direccion;

    @Email
    private String correoElectronico;
}