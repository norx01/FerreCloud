package com.ferrecloud.ferrecloud.controller;

import com.ferrecloud.ferrecloud.dto.VentaDTO;
import com.ferrecloud.ferrecloud.model.Venta;
import com.ferrecloud.ferrecloud.service.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public List<Venta> listar() {
        return ventaService.listar();
    }

    @PostMapping
    public Venta registrar(@RequestBody VentaDTO dto) {
        return ventaService.registrar(dto); //
    }
}