package com.ferrecloud.ferrecloud.view;

import com.ferrecloud.ferrecloud.model.Clientes;
import com.ferrecloud.ferrecloud.service.ClientesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClientesView {

    private final ClientesService service;

    public ClientesView(ClientesService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("clientes", service.listar());

        return "clientes/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("cliente", new Clientes());

        return "clientes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Clientes cliente) {

        service.guardar(cliente);

        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model model) {

        model.addAttribute("cliente",
                service.buscarPorId(id));

        return "clientes/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {

        service.eliminar(id);

        return "redirect:/clientes";
    }
}
