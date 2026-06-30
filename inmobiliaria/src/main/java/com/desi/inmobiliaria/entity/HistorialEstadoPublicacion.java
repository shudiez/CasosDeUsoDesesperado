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
@Table(name = "historial_estados_publicaciones")


public class HistorialEstadoPublicacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoPublicacion estado;
	
	@Column(name = "fecha_hora", nullable = false)
	private LocalDateTime fechaHora;
	

	@ManyToOne
	@JoinColumn(name = "publicacion_id", nullable = false)
	private Publicacion publicacion;

	
	
	public HistorialEstadoPublicacion() {
	}

	public HistorialEstadoPublicacion(EstadoPublicacion estado, LocalDateTime fechaHora, Publicacion publicacion) {
		this.estado = estado;
		this.fechaHora = fechaHora;
		this.publicacion = publicacion;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoPublicacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPublicacion estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}
}