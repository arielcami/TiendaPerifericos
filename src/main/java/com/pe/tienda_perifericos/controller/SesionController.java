package com.pe.tienda_perifericos.controller;

import com.pe.tienda_perifericos.model.Usuario;
import com.pe.tienda_perifericos.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SesionController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "user/login"; // Devuelve la vista de login (login.html)
    }

    @PostMapping("/login")
    public String login(@RequestParam String nombre, @RequestParam String contrasena,
                        HttpSession session, Model model) {

        // Intentar encontrar el usuario por su nombre
        Usuario usuario = usuarioService.findByNombre(nombre);

        // Verificar si el usuario existe y la contraseña es correcta
        if (usuario != null && usuario.getPassword().equals(contrasena)) {

            // Verificar el estado e intentos de login
            String estadoYIntentos = usuarioService.verificarEstadoYIntentos(usuario);

            if (estadoYIntentos != null) {
                model.addAttribute("error", estadoYIntentos);
                return "user/login"; // Si hay un error, regresar al login
            }

            // Restablecer los intentos de login al ingresar correctamente
            usuarioService.restablecerIntentos(usuario);

            // Guardar el objeto usuario completo en la sesión
            session.setAttribute("usuarioLogueado", usuario); // Guardar el objeto completo Usuario

            return "redirect:/index"; // Redirigir al usuario al índice o página principal
        } else {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos.");
            return "user/login"; // Si la autenticación falla, regresar al login
        }
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        // Invalidar la sesión del servidor
        session.invalidate();
        
        // Agregar mensaje de éxito para ser mostrado
        redirectAttributes.addFlashAttribute("success", "Has cerrado sesión exitosamente.");
        
        // Redirigir a la página de login
        return "redirect:/user/login";
    }

}
