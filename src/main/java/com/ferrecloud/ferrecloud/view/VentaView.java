package com.ferrecloud.ferrecloud.view;

import com.ferrecloud.ferrecloud.service.ClientesService;
import com.ferrecloud.ferrecloud.service.InventarioService;
import com.ferrecloud.ferrecloud.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ventas")
public class VentaView {

    private final VentaService ventaService;
    private final ClientesService clientesService;
    private final InventarioService inventarioService;

    public VentaView(VentaService ventaService,
                     ClientesService clientesService,
                     InventarioService inventarioService) {
        this.ventaService = ventaService;
        this.clientesService = clientesService;
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listar());
        return "ventas/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("clientes", clientesService.listar());
        model.addAttribute("productos", inventarioService.listar());
        return "ventas/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        ventaService.eliminar(id);
        return "redirect:/ventas";
    }
}