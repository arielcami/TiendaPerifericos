package com.pe.tienda_perifericos.controller;

import com.pe.tienda_perifericos.model.Producto;
import com.pe.tienda_perifericos.model.Usuario;
import com.pe.tienda_perifericos.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public String buscarProductos(@RequestParam(required = false) String nombre, Model model, HttpSession session) {
    	
        List<Producto> productos;

     // Si el parámetro nombre es nulo o vacío, obtenemos todos los productos
        if (nombre != null && !nombre.isEmpty()) {
            productos = productoService.buscarProductosPorNombre(nombre);
        } else {
            productos = productoService.obtenerTodosLosProductos(); // Método para obtener todos los productos
        }

        // Obtener el objeto usuario completo desde la sesión
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        // Si el usuario está logueado, añadimos su nombre al modelo
        if (usuarioLogueado != null) {
            model.addAttribute("username", usuarioLogueado.getNombre());
        }

        model.addAttribute("productos", productos);
        model.addAttribute("busqueda", nombre); // Pasamos el valor de búsqueda (puede ser null)
        return "user/productos";  // Ruta completa a la vista productos.html en templates/user
    }


 // Método para mostrar la página de inicio (index)
    @GetMapping("/index")
    public String showIndex(Model model, HttpSession session) {
        // Obtener el objeto usuario completo desde la sesión
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        // Si el usuario está logueado, añadimos su nombre al modelo
        if (usuarioLogueado != null) {
            model.addAttribute("username", usuarioLogueado.getNombre());
        }

        // Obtener todos los productos para la vista
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        model.addAttribute("productos", productos);

        return "user/index";  // Retorna la vista con los productos
    }
}
