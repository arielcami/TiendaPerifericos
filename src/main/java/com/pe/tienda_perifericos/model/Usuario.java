package com.pe.tienda_perifericos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;

    @Column(name = "usuario_nombre")
    private String nombre;

    @Column(name = "usuario_pass")
    private String password;

    @Column(name = "usuario_correo")
    private String correo;

    @Column(name = "intentos_login")
    private Integer intentosLogin;

    @Column(name = "estado")
    private Integer estado;

    // Constructores
    public Usuario() {
        super();
    }

	public Usuario(Integer id, String nombre, String password, String correo, Integer intentosLogin, Integer estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.password = password;
		this.correo = correo;
		this.intentosLogin = intentosLogin;
		this.estado = estado;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Integer getIntentosLogin() {
		return intentosLogin;
	}

	public void setIntentosLogin(Integer intentosLogin) {
		this.intentosLogin = intentosLogin;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
}