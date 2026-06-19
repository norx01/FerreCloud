package com.ferrecloud.ferrecloud.view;

import com.ferrecloud.ferrecloud.service.ReporteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reportes")
public class ReporteView {

    private final ReporteService reporteService;

    public ReporteView(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ventasHoy", reporteService.ventasHoy());
        model.addAttribute("ventasSemana", reporteService.ventasSemana());
        model.addAttribute("ventasMes", reporteService.ventasMes());
        model.addAttribute("stockBajo", reporteService.productosStockBajo());
        return "reportes/list";
    }
}