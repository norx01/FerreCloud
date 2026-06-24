package com.ferrecloud.ferrecloud.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SesionFiltro implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();

        boolean esRutaPublica = uri.equals("/login")
                || uri.startsWith("/css")
                || uri.startsWith("/js");

        if (esRutaPublica) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);
        boolean logueado = session != null && session.getAttribute("usuarioLogueado") != null;

        if (!logueado) {
            response.sendRedirect("/login");
            return;
        }

        chain.doFilter(req, res);
    }
}