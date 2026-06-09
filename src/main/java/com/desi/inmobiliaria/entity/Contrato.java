
package com.desi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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


@Entity
public class Contrato {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	private Propiedad propiedad;
	
	@ManyToOne(optional = false)
	private Persona inquilino;
	
	private LocalDate fechaInicio;
	
	private Integer duracionMeses;
	
	private BigDecimal importeMensual;
	
	private Integer diaVencimientoMensual;
	
	@Column(length = 2000)
	private String descripcion;
	
	@Enumerated(EnumType.STRING)
    private EstadoContrato estado;
	
	private boolean eliminado = false;
	
	
	@OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HistorialEstadoContrato>historialEstados = new ArrayList<>();
	
	public Contrato() {	}

	public void agregarCambioEstado(EstadoContrato nuevoEstado) {
		HistorialEstadoContrato h= new HistorialEstadoContrato(this, nuevoEstado);
		historialEstados.add(h);
		this.estado = nuevoEstado;
	}
	
	
	// --- Getters y Setters ---
    public Long getId() { return id; }

    public Propiedad getPropiedad() { return propiedad; }
    public void setPropiedad(Propiedad propiedad) { this.propiedad = propiedad; }

    public Persona getInquilino() { return inquilino; }
    public void setInquilino(Persona inquilino) { this.inquilino = inquilino; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public Integer getDuracionMeses() { return duracionMeses; }
    public void setDuracionMeses(Integer duracionMeses) { this.duracionMeses = duracionMeses; }

    public BigDecimal getImporteMensual() { return importeMensual; }
    public void setImporteMensual(BigDecimal importeMensual) { this.importeMensual = importeMensual; }

    public Integer getDiaVencimientoMensual() { return diaVencimientoMensual; }
    public void setDiaVencimientoMensual(Integer diaVencimientoMensual) { this.diaVencimientoMensual = diaVencimientoMensual; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoContrato getEstado() { return estado; }
    public void setEstado(EstadoContrato estado) { this.estado = estado; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public List<HistorialEstadoContrato> getHistorialEstados() { return historialEstados; }
}
	
	

