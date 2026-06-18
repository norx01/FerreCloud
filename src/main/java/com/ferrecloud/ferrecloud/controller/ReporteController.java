package com.ferrecloud.ferrecloud.controller;

import com.ferrecloud.ferrecloud.model.Venta;
import com.ferrecloud.ferrecloud.model.producto;
import com.ferrecloud.ferrecloud.service.ReporteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/ventas/hoy")
    public List<Venta> hoy() { return reporteService.ventasHoy(); }

    @GetMapping("/ventas/semana")
    public List<Venta> semana() { return reporteService.ventasSemana(); }

    @GetMapping("/ventas/mes")
    public List<Venta> mes() { return reporteService.ventasMes(); }

    @GetMapping("/stock-bajo")
    public List<producto> stockBajo() { return reporteService.productosStockBajo(); }
}