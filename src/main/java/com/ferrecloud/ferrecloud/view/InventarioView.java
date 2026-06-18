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
        // Pasamos la lista de productos a la vista con el nombre "productos"
        model.addAttribute("productos", service.listar());
        return "inventario/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        // Pasamos un objeto vacío a la vista para el formulario
        model.addAttribute("producto", new producto());
        return "inventario/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute producto producto) {
        service.guardar(producto);
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model) {
        // Buscamos el producto por ID y lo pasamos al formulario para editarlo
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
        // Esto retornaría a una vista HTML dedicada a alertas, puedes crearla luego
        return "inventario/alertas";
    }
}