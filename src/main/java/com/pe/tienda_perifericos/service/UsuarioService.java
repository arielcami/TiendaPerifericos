package com.pe.tienda_perifericos.service;

import com.pe.tienda_perifericos.dto.UsuarioDTO;
import com.pe.tienda_perifericos.model.Usuario;
import com.pe.tienda_perifericos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	// Lógica de validación de entrada del usuario
	public boolean validarEntradaUsuario(String nombre, String contrasena, String confirmarContrasena, String correo) {

		// No se permiten espacios delante ni letras tildadas mayús/minús, tampoco ñ ni
		// Ñ
		if (!nombre.matches("^[^\\s]+$") || nombre.matches(".*[áéíóúÁÉÍÓÚñÑ].*")) {
			return false;
		}

		// Solo estos dominios de correo son válidos
		if (!correo.matches("^[^\\s]+@(gmail.com|hotmail.com|outlook.com|yahoo.es|outlook.es|certus.edu.pe)$")) {
			return false;
		}

		// Caracteres permitidos para contraseña, todo lo demás que no figure aquí, está
		// restringido
		if (!contrasena.equals(confirmarContrasena) || contrasena.length() < 8 || !contrasena.matches(".*[A-Z].*")
				|| !contrasena.matches(".*[a-z].*") || !contrasena.matches(".*[0-9].*")
				|| !contrasena.matches(".*[!@#$%^&*().].*")) {
			return false;
		}

		return true; // Retorna verdadero si todas las validaciones pasaron
	}

	public void crearUsuario(UsuarioDTO usuarioDTO) {
		Usuario nuevoUsuario = new Usuario();
		nuevoUsuario.setNombre(usuarioDTO.getUsername().toLowerCase());
		nuevoUsuario.setPassword(usuarioDTO.getPassword());
		nuevoUsuario.setCorreo(usuarioDTO.getCorreo().toLowerCase());
		nuevoUsuario.setEstado(1); // El usuario es creado con estado activo
		nuevoUsuario.setIntentosLogin(0); // intentos_login no se estaba seteando, pasaba como NULL

		usuarioRepository.save(nuevoUsuario);
	}

	public boolean nombreUsuarioExiste(String nombre) {
		return usuarioRepository.findByNombre(nombre) != null;
	}

	public boolean correoExiste(String correo) {
		return usuarioRepository.findByCorreo(correo) != null;
	}

	// Método unificado para comprobar si el nombre de usuario o el correo ya
	// existen
	public String validarUsuarioYCorreo(String nombre, String correo) {
		if (nombreUsuarioExiste(nombre) || correoExiste(correo)) {
			return "Error: Ya hay campos en uso en esta combinación, intenta con nuevos datos.";
		}
		return null; // Sin errores
	}

	public Usuario findByNombre(String nombre) {
		return usuarioRepository.findByNombre(nombre);
	}

	// Verificar estado e intentos
	public String verificarEstadoYIntentos(Usuario usuario) {

		// Cuenta activa
		if (usuario.getEstado() == 1) {

			// Verificar si los intentos de login han alcanzado el máximo permitido
			if (usuario.getIntentosLogin() >= 5) {

				usuario.setEstado(0); // Suspender la cuenta
				usuario.setIntentosLogin(0); // Se reinicia el contador de intentos, lo que importa es que Estado ya es 0
				usuarioRepository.save(usuario); // Guardar los cambios en la base de datos

				return "Tu cuenta se suspendió temporalmente debido a múltiples intentos fallidos. "
						+ "Envía un correo a p.a.l.store.atencion@gmail.com con un resumen de tu caso "
						+ " para empezar con el proceso de recuperación.";

			}

		} else {
		// Cuenta bloqueada
			return "Tu cuenta se suspendió temporalmente, envía un correo con todos tus datos a: "
					+ "p.a.l.store.atencion@gmail.com para empezar con el proceso de recuperación de tu contraseña.";

		}

		return null; // No hay errores
	}

	// Restablecer intentos de login
	public void restablecerIntentos(Usuario usuario) {
		usuario.setIntentosLogin(0); // Restablecer el contador de intentos
		usuarioRepository.save(usuario); // Guardar los cambios en la base de datos
	}

	// Incrementar intentos de login
	public void incrementarIntentos(Usuario usuario) {
		usuario.setIntentosLogin(usuario.getIntentosLogin() + 1); // Incrementar el contador de intentos
		usuarioRepository.save(usuario); // Guardar los cambios en la base de datos
	}
}
