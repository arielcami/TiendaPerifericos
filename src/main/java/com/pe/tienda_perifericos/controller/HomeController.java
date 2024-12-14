package com.pe.tienda_perifericos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Redirigir a la página de login cuando acceden a la raíz del sitio
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/user/index";  // Redirige al formulario de login
    }
}
