package com.desi.inmobiliaria.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
public class Contrato {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@NotNull(message = "La propiedad es obligatoria")
	private Propiedad propiedad;

	@ManyToOne(optional = false)
	@NotNull(message = "El inquilino es obligatorio")
	private Persona inquilino;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "La fecha de inicio es obligatoria")
	private LocalDate fechaInicio;

	@NotNull(message = "La duración es obligatoria")
	@Positive(message = "La duración debe ser mayor a 0 meses")
	private Integer duracionMeses;
	@NotNull(message = "El importe es obligatorio")
	@Positive(message = "El importe debe ser un número positivo") // <-- Evita negativos y cero
	private BigDecimal importeMensual;
	@NotNull(message = "El día de vencimiento es obligatorio")
	@Min(value = 1, message = "El día mínimo es 1")
	@Max(value = 31, message = "El día máximo es 31")
	private Integer diaVencimientoMensual;

	@Column(length = 2000)
	private String descripcion;

	@Enumerated(EnumType.STRING)
	private EstadoContrato estado;

	private boolean eliminado = false;

	@OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HistorialEstadoContrato> historialEstados = new ArrayList<>();

	public Contrato() {
	}

	public void agregarCambioEstado(EstadoContrato nuevoEstado) {
		HistorialEstadoContrato h = new HistorialEstadoContrato(this, nuevoEstado);
		historialEstados.add(h);
		this.estado = nuevoEstado;
	}

	// --- Getters y Setters ---
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Propiedad getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(Propiedad propiedad) {
		this.propiedad = propiedad;
	}

	public Persona getInquilino() {
		return inquilino;
	}

	public void setInquilino(Persona inquilino) {
		this.inquilino = inquilino;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Integer getDuracionMeses() {
		return duracionMeses;
	}

	public void setDuracionMeses(Integer duracionMeses) {
		this.duracionMeses = duracionMeses;
	}

	public BigDecimal getImporteMensual() {
		return importeMensual;
	}

	public void setImporteMensual(BigDecimal importeMensual) {
		this.importeMensual = importeMensual;
	}

	public Integer getDiaVencimientoMensual() {
		return diaVencimientoMensual;
	}

	public void setDiaVencimientoMensual(Integer diaVencimientoMensual) {
		this.diaVencimientoMensual = diaVencimientoMensual;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoContrato getEstado() {
		return estado;
	}

	public void setEstado(EstadoContrato estado) {
		this.estado = estado;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public List<HistorialEstadoContrato> getHistorialEstados() {
		return historialEstados;
	}
}