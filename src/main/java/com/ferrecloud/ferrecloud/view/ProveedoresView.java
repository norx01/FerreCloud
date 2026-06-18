package com.ferrecloud.ferrecloud.view;

import com.ferrecloud.ferrecloud.model.Proveedores;
import com.ferrecloud.ferrecloud.service.ProveedoresService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proveedores")
public class ProveedoresView {

    private final ProveedoresService service;

    public ProveedoresView(ProveedoresService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", service.listar());
        // Apunta a la plantilla HTML donde mostrarás la tabla
        return "proveedores/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("proveedor", new Proveedores());
        // Apunta a la plantilla HTML del formulario
        return "proveedores/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Proveedores proveedor) {
        service.guardar(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model) {
        model.addAttribute("proveedor", service.buscarPorId(id));
        return "proveedores/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        try {
            service.eliminar(id);
        } catch (IllegalStateException e) {
            return "redirect:/proveedores?error=restriccion";
        }

        return "redirect:/proveedores";
    }
}