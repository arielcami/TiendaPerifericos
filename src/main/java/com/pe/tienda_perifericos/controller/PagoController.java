package com.pe.tienda_perifericos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pagos")
public class PagoController {
	
	@PostMapping("/realizarPago")
	public String abrirPago(Model model) {
		// Recuperar el usuario logueado desde la sesión
        //Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");     
        model.addAttribute("username", "visitante");
		return "redirect:/user/index";
	}
	
	
	// Al clickear el botón de "Registrar Pago"
	@PostMapping("/procesar")
	public String pagarAhora() {		
		return "";
	}
	
}
