package com.ferrecloud.ferrecloud.view;

import com.ferrecloud.ferrecloud.model.Usuarios;
import com.ferrecloud.ferrecloud.service.UsuariosService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginView {

    private final UsuariosService service;

    public LoginView(UsuariosService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String documento,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {

        Usuarios usuario = service.login(documento, password);

        if (usuario == null) {
            model.addAttribute("error", "Documento o contraseña incorrectos.");
            return "login";
        }

        session.setAttribute("usuarioLogueado", usuario);
        return "redirect:/clientes";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}