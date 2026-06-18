package com.ferrecloud.ferrecloud.dto;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProveedoresDTO
{
    @NotBlank
    private String nombre;

    @NotBlank
    private String direccion;

    @NotBlank
    private String telefono;

    @NotBlank
    private String productosSuministrados;

}
