package com.pe.tienda_perifericos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 16, message = "El nombre de usuario debe tener entre 4 y 16 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "El nombre de usuario no debe contener espacios, tildes ni caracteres especiales")
    private String username;

    @NotNull(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W.]).+$", message = "La contraseña debe contener mayúsculas, minúsculas, números y al menos un caracter especial.")
    private String password;

    @NotBlank(message = "La confirmación de contraseña es obligatoria")
    private String confirm_password;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Correo electrónico inválido")
    @Pattern(regexp = "^[^\\s]+@(gmail\\.com|hotmail\\.com|outlook\\.com|yahoo\\.es|outlook\\.es|certus\\.edu\\.pe)$",
             message = "Dominio no permitido.")
    private String correo;

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase(); // Convierte a minúsculas pero no remueve tildes o caracteres no permitidos
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo.toLowerCase();
    }
}
