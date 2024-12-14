package com.pe.tienda_perifericos.controller;

import com.pe.tienda_perifericos.dto.UsuarioDTO;
import com.pe.tienda_perifericos.model.Producto;
import com.pe.tienda_perifericos.model.Usuario;
import com.pe.tienda_perifericos.service.ProductoService;
import com.pe.tienda_perifericos.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private HttpSession session;

    // Mostrar formulario de registro
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "user/register";
    }

    // Registrar un nuevo usuario
    @PostMapping("/register")
    public String registerUser(@Valid UsuarioDTO usuarioDTO, BindingResult result, Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("error", "Datos inválidos. Verifica los campos.");
            return "user/register";
        }

        if (!usuarioService.validarEntradaUsuario(usuarioDTO.getUsername(), usuarioDTO.getPassword(),
                usuarioDTO.getConfirm_password(), usuarioDTO.getCorreo())) {
            model.addAttribute("error", "Todos o algunos de los datos ingresados fueron incorrectos.");
            return "user/register";
        }

        String validationError = usuarioService.validarUsuarioYCorreo(usuarioDTO.getUsername(), usuarioDTO.getCorreo());
        if (validationError != null) {
            model.addAttribute("error", validationError);
            return "user/register";
        }

        usuarioService.crearUsuario(usuarioDTO);

        redirectAttributes.addFlashAttribute("success", "Usuario registrado correctamente.");
        return "redirect:/user/login";
    }

    // Mostrar formulario de login
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "user/login";
    }

    
    // Método para iniciar sesión de usuario
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model,
                            RedirectAttributes redirectAttributes) {

        // Validar que el nombre de usuario solo contenga letras minúsculas de a-z, la 'ñ' también queda fuera.
        if (!username.matches("^[a-z]+$")) {
            model.addAttribute("error", "Nombre de usuario inválido.");
            return "user/login";
        }

        Usuario usuario = usuarioService.findByNombre(username);

        if (usuario == null) {
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos.");
            return "user/login";
        }

        // Verificar estado de la cuenta y número de intentos
        String mensajeError = usuarioService.verificarEstadoYIntentos(usuario);
        if (mensajeError != null) {
            model.addAttribute("error", mensajeError);
            return "user/login";
        }

        // Verificar contraseña
        if (usuario.getPassword().equals(password)) {
            usuarioService.restablecerIntentos(usuario);

            // Aquí estamos guardando el objeto completo de usuario en la sesión
            session.setAttribute("usuarioLogueado", usuario); // Almacenar el objeto usuario completo

            redirectAttributes.addFlashAttribute("success", "Inicio de sesión exitoso.");
            return "redirect:/user/index"; // Redirige al índice después de login exitoso
        } else {
            usuarioService.incrementarIntentos(usuario);
            model.addAttribute("error", "Nombre de usuario o contraseña incorrectos.");
            return "user/login";
        }
    }

    /*
    // Cerrar sesión de usuario
    @GetMapping("/user/logout")
    public String logoutUser(RedirectAttributes redirectAttributes) {
        session.invalidate(); // Invalida la sesión actual
        redirectAttributes.addFlashAttribute("success", "Has cerrado sesión exitosamente.");
        return "redirect:/user/login"; // Redirige al login después de cerrar sesión
    }
    
    */

    // Ver la página de inicio con productos
    @GetMapping("/index")
    public String showIndex(Model model) {
        // Obtener el objeto usuario completo desde la sesión
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado != null) {
            model.addAttribute("username", usuarioLogueado.getNombre());
        }

        // Cargar productos para mostrarlos en la vista
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        model.addAttribute("productos", productos);

        return "user/index"; // Devuelve la vista con los productos
    }
}
