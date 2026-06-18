package com.ferrecloud.ferrecloud.view;

import com.ferrecloud.ferrecloud.model.producto;
import com.ferrecloud.ferrecloud.service.InventarioService;
import com.ferrecloud.ferrecloud.service.ProveedoresService; // Importamos el servicio de proveedores
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventario")
public class InventarioView {

    private final InventarioService service;
    private final ProveedoresService proveedoresService; // Agregamos la dependencia

    // Inyectamos ambos servicios en el constructor
    public InventarioView(InventarioService service, ProveedoresService proveedoresService) {
        this.service = service;
        this.proveedoresService = proveedoresService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", service.listar());
        model.addAttribute("alertas", service.obtenerProductosConStockBajo());
        return "inventario/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new producto());

        // Buscamos todos los proveedores y los enviamos a la vista
        model.addAttribute("proveedores", proveedoresService.listar());

        return "inventario/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute producto producto) {
        if (producto.getId() != null && producto.getId().isEmpty()) {
            producto.setId(null);
        }
        service.guardar(producto);
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model) {
        model.addAttribute("producto", service.buscarPorId(id));

        // También debemos enviar la lista de proveedores cuando vamos a editar
        model.addAttribute("proveedores", proveedoresService.listar());

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