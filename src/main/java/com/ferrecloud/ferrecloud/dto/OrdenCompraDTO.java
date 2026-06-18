package com.ferrecloud.ferrecloud.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrdenCompraDTO {

    @NotBlank(message = "El cliente es obligatorio")
    private String clienteId;

    @NotBlank(message = "El vendedor es obligatorio")
    private String vendedorId;

    @NotEmpty(message = "La orden debe tener al menos un producto")
    @Valid
    private List<ItemOrdenDTO> productos;
}