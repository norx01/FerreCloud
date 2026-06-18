package com.ferrecloud.ferrecloud.dto;

import lombok.Data;
import java.util.List;

@Data
public class VentaDTO {
        private String clienteId;
        private String empleadoId;
        private List<ItemOrdenDTO> productos;
    }

