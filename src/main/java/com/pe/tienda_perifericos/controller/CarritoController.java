package com.pe.tienda_perifericos.controller;

import com.pe.tienda_perifericos.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarritoController {

    @Autowired
    private HttpSession session;

    @GetMapping("/user/index/carrito")
    public String abrirCarrito(Model model) {
        // Recuperar el usuario logueado desde la sesión
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        // Verificar si el usuario está logueado y agregar el nombre al modelo
        if (usuarioLogueado != null) {
            model.addAttribute("username", usuarioLogueado.getNombre());
        }

        // Retornar la vista del carrito
        return "user/carrito";
    }
}
