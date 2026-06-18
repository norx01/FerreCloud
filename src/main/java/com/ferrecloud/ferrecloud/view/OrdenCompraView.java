package com.ferrecloud.ferrecloud.view;

import com.ferrecloud.ferrecloud.model.OrdenCompra;
import com.ferrecloud.ferrecloud.service.ClientesService;
import com.ferrecloud.ferrecloud.service.InventarioService;
import com.ferrecloud.ferrecloud.service.OrdenCompraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ordenes")
public class OrdenCompraView {

    private final OrdenCompraService ordenService;
    private final ClientesService clientesService;
    private final InventarioService inventarioService;

    public OrdenCompraView(OrdenCompraService ordenService,
                           ClientesService clientesService,
                           InventarioService inventarioService) {
        this.ordenService = ordenService;
        this.clientesService = clientesService;
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public String listar(Model model) {
        List<OrdenCompra> ordenes = ordenService.listar();
        model.addAttribute("ordenes", ordenes);
        return "ordenes/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("clientes", clientesService.listar());
        model.addAttribute("productos", inventarioService.listar());
        return "ordenes/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        ordenService.eliminar(id);
        return "redirect:/ordenes";
    }
}