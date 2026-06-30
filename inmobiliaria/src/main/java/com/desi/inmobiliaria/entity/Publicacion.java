package com.desi.inmobiliaria.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;     
import jakarta.persistence.EnumType;       
import jakarta.persistence.OneToMany;      
import jakarta.persistence.ManyToOne; // ¡Cambiado!       
import jakarta.persistence.JoinColumn;      
import jakarta.persistence.CascadeType;   
import java.math.BigDecimal;               
import java.time.LocalDate;
import java.util.List;                     
import java.util.ArrayList;                

@Entity
@Table(name = "publicaciones")

public class Publicacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name = "precio_mensual", nullable = false)
	private BigDecimal precioMensual;
	
	@Column(name = "condiciones_alquiler", columnDefinition = "TEXT", nullable = false)
	private String condiciones;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String descripcion;
	
	@Column(name = "fecha_publicacion", nullable = false)
	private LocalDate fechaPublicacion;
	
	@Column(name = "eliminada", nullable = false)
	private boolean eliminada = false;

	@Enumerated(EnumType.STRING)
	private EstadoPublicacion estado;

	
	@ManyToOne
	@JoinColumn(name = "propiedad_id", nullable = false)
	private Propiedad propiedad;

	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HistorialEstadoPublicacion> historialEstados = new ArrayList<>();
	
	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Visitas> visitas = new ArrayList<>();

	
	public Publicacion() {
	}

	
	public Publicacion(BigDecimal precioMensual, String condiciones, String descripcion, 
			LocalDate fechaPublicacion, boolean eliminada, EstadoPublicacion estado, Propiedad propiedad) {
		this.precioMensual = precioMensual;
		this.condiciones = condiciones;
		this.descripcion = descripcion;
		this.fechaPublicacion = fechaPublicacion;
		this.eliminada = eliminada;
		this.estado = estado;
		this.propiedad = propiedad;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrecioMensual() {
		return precioMensual;
	}

	public void setPrecioMensual(BigDecimal precioMensual) {
		this.precioMensual = precioMensual;
	}

	public String getCondiciones() {
		return condiciones;
	}

	public void setCondiciones(String condiciones) {
		this.condiciones = condiciones;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public boolean isEliminada() { 
		return eliminada;
	}

	public void setEliminada(boolean eliminada) {
		this.eliminada = eliminada;
	}

	public EstadoPublicacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoPublicacion estado) {
		this.estado = estado;
	}

	public Propiedad getPropiedad() {
		return propiedad;
	}

	public void setPropiedad(Propiedad propiedad) {
		this.propiedad = propiedad;
	}

	public List<HistorialEstadoPublicacion> getHistorialEstados() {
		return historialEstados;
	}

	public void setHistorialEstados(List<HistorialEstadoPublicacion> historialEstados) {
		this.historialEstados = historialEstados;
	}

	public List<Visitas> getVisitas() {
		return visitas;
	}

	public void setVisitas(List<Visitas> visitas) {
		this.visitas = visitas;
	}
}