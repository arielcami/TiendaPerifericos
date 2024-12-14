package com.pe.tienda_perifericos.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {

	// Atributos
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private int id;

    @Column(name = "pedido_numero")
    private String numero;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "fecha_pedido")
    private LocalDateTime fecha_de_pedido;

    @Column(name = "fecha_entrega")
    private Date fecha_de_entrega;

    @Column(name = "total")
    private double total;

	// Constructores
	public Pedido() {
		super();
	}
	
	public Pedido(int id, String numero, String detalle, LocalDateTime fecha_de_pedido, Date fecha_de_entrega, double total) {
		super();
		this.id = id;
		this.numero = numero;
		this.detalle = detalle;
		this.fecha_de_pedido = fecha_de_pedido;
		this.fecha_de_entrega = fecha_de_entrega;
		this.total = total;
	}


	// Getters y Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public LocalDateTime getFecha_de_pedido() {
		return fecha_de_pedido;
	}

	public void setFecha_de_pedido(LocalDateTime fecha_de_pedido) {
		this.fecha_de_pedido = fecha_de_pedido;
	}

	public Date getFecha_de_entrega() {
		return fecha_de_entrega;
	}

	public void setFecha_de_entrega(Date fecha_de_entrega) {
		this.fecha_de_entrega = fecha_de_entrega;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", numero=" + numero + ", detalle=" + detalle + ", fecha_de_pedido="
				+ fecha_de_pedido + ", fecha_de_entrega=" + fecha_de_entrega + ", total=" + total + "]";
	}

}
