package com.desi.inmobiliaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;     
import jakarta.persistence.EnumType;       
import jakarta.persistence.ManyToOne;       
import jakarta.persistence.JoinColumn;      
import java.time.LocalDateTime;

@Entity
@Table(name = "visitas")
public class Visitas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "fecha_hora", nullable = false)
	private LocalDateTime fechaHora;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoVisita estado;
	
	
	@ManyToOne
	@JoinColumn(name = "publicacion_id", nullable = false)
	private Publicacion publicacion; 

	
	
	public Visitas() {
	}

	public Visitas(LocalDateTime fechaHora, EstadoVisita estado, Publicacion publicacion) {
		this.fechaHora = fechaHora;
		this.estado = estado;
		this.publicacion = publicacion;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public EstadoVisita getEstado() {
		return estado;
	}

	public void setEstado(EstadoVisita estado) {
		this.estado = estado;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}
}