package com.desi.inmobiliaria.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
//Guarda los cambios de estado que tuvo una propiedad
public class HistorialEstadoPropiedad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// ID único del registro
	private Long id;

	// Estado que tenía la propiedad en ese momento
	@Enumerated(EnumType.STRING)
	private EstadoDisponibilidad estado;

	// Fecha y hora en que se produjo el cambio de estado
	private LocalDateTime fechaHora;

	// Una propiedad puede tener muchos cambios de estado a lo largo del tiempo
	@ManyToOne
	private Propiedad propiedad;

	// constructor vacio
	public HistorialEstadoPropiedad() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EstadoDisponibilidad getEstado() {
		return estado;
	}

	public void setEstado(EstadoDisponibilidad estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Propiedad getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(Propiedad propiedad) {
		this.propiedad = propiedad;
	}

}
