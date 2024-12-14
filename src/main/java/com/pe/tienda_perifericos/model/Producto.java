package com.pe.tienda_perifericos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

	// Atributos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private int id;
	
	@Column(name = "producto_nombre")
    private String nombre;

	@Column(name = "producto_desc")
    private String descripcion;

	@Column(name = "marca")
    private String marca;

	@Column(name = "precio")
    private double precio;

	@Column(name = "imagen	")
    private String imagen;

	@Column(name = "stock")
    private int stock;

	@Column(name = "estado")
    private int estado;

	// Constructores
	public Producto() {
		super();
	}

	public Producto(int id, String nombre,
			String descripcion, String marca, double precio, String imagen, int stock, int estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.marca = marca;
		this.precio = precio;
		this.imagen = imagen;
		this.stock = stock;
		this.estado = estado;
	}

	// Getters y Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", marca=" + marca
				+ ", precio=" + precio + ", imagen=" + imagen + ", stock=" + stock + ", estado=" + estado + "]";
	}

}