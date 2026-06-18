package com.ferrecloud.ferrecloud.view;

import com.ferrecloud.ferrecloud.model.producto;
import com.ferrecloud.ferrecloud.service.InventarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventario")
public class InventarioView {

    private final InventarioService service;

    public InventarioView(InventarioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", service.listar());
        return "inventario/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new producto());
        return "inventario/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute producto producto) {
        // --- LA SOLUCIÓN ESTÁ AQUÍ ---
        // Si el ID es un texto vacío (""), lo volvemos null para que MongoDB cree uno nuevo.
        if (producto.getId() != null && producto.getId().isEmpty()) {
            producto.setId(null);
        }

        service.guardar(producto);
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model) {
        model.addAttribute("producto", service.buscarPorId(id));
        return "inventario/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        service.eliminar(id);
        return "redirect:/inventario";
    }

    @GetMapping("/alertas")
    public String alertasStock(Model model) {
        model.addAttribute("productos", service.obtenerProductosConStockBajo());
        return "inventario/alertas";
    }
}