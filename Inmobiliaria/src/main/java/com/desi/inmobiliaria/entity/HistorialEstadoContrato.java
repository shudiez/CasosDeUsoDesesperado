package com.desi.inmobiliaria.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class HistorialEstadoContrato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	private Contrato contrato;
	
	@Enumerated(EnumType.STRING)
	private EstadoContrato estado;
	
	private LocalDate fechaHora = LocalDate.now();
	
	
	
	public HistorialEstadoContrato() {	}

	
	
	public HistorialEstadoContrato(Contrato contrato, EstadoContrato estado) {
		this.contrato=contrato;
		this.estado=estado;
	}
	
	
	
}