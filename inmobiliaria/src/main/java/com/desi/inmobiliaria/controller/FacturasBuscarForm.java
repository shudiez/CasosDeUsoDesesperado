package com.desi.inmobiliaria.controller;

import java.time.LocalDate;
import com.desi.inmobiliaria.entity.EstadoFactura;

public class FacturasBuscarForm {

	private Long contratoId;
	private Long inquilinoId;
	private Long propiedadId;
	private EstadoFactura estado;
	private LocalDate fechaDesde;
	private LocalDate fechaHasta;
	
	public Long getContratoId() {
		return contratoId;
	}
	public void setContratoId(Long contratoId) {
		this.contratoId = contratoId;
	}
	public Long getInquilinoId() {
		return inquilinoId;
	}
	public void setInquilinoId(Long inquilinoId) {
		this.inquilinoId = inquilinoId;
	}
	public Long getPropiedadId() {
		return propiedadId;
	}
	public void setPropiedadId(Long propiedadId) {
		this.propiedadId = propiedadId;
	}
	public EstadoFactura getEstado() {
		return estado;
	}
	public void setEstado(EstadoFactura estado) {
		this.estado = estado;
	}
	public LocalDate getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(LocalDate fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public LocalDate getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(LocalDate fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
}
