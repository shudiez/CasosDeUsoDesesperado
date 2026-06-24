package com.desi.inmobiliaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany; 
import jakarta.persistence.CascadeType; 
import java.util.List; 
import java.util.ArrayList; 

@Entity
@Table(name = "propiedades")
public class Propiedad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String direccion;
	
	@Column(name = "cantidad_ambientes")
	private Integer cantidadAmbientes;
	
	@Column(name = "metros_cuadrados")
	private Double metrosCuadrados;
	
	@Column(columnDefinition = "TEXT")
	private String descripcion;
	
	@Column(columnDefinition = "TEXT")
	private String comodidades;
	
	@Column(nullable = false)
	private boolean eliminada = false;


	@OneToMany(mappedBy = "propiedad", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Publicacion> publicaciones = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoPropiedad tipo;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_disponibilidad", nullable = false)
	private EstadoDisponibilidad estadoDisponibilidad;


	public Propiedad() {
	}


	public Propiedad(String direccion, Integer cantidadAmbientes, Double metrosCuadrados, 
			String descripcion, String comodidades, boolean eliminada, TipoPropiedad tipo, 
			EstadoDisponibilidad estadoDisponibilidad) {
		this.direccion = direccion;
		this.cantidadAmbientes = cantidadAmbientes;
		this.metrosCuadrados = metrosCuadrados;
		this.descripcion = descripcion;
		this.comodidades = comodidades;
		this.eliminada = eliminada;
		this.tipo = tipo;
		this.estadoDisponibilidad = estadoDisponibilidad;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getCantidadAmbientes() {
		return cantidadAmbientes;
	}

	public void setCantidadAmbientes(Integer cantidadAmbientes) {
		this.cantidadAmbientes = cantidadAmbientes;
	}

	public Double getMetrosCuadrados() {
		return metrosCuadrados;
	}

	public void setMetrosCuadrados(Double metrosCuadrados) {
		this.metrosCuadrados = metrosCuadrados;
	}

	public String getComodidades() {
		return comodidades;
	}

	public void setComodidades(String comodidades) {
		this.comodidades = comodidades;
	}

	public boolean isEliminada() {
		return eliminada;
	}

	public void setEliminada(boolean eliminada) {
		this.eliminada = eliminada;
	}

	public TipoPropiedad getTipo() {
		return tipo;
	}

	public void setTipo(TipoPropiedad tipo) {
		this.tipo = tipo;
	}

	public EstadoDisponibilidad getEstadoDisponibilidad() {
		return estadoDisponibilidad;
	}

	public void setEstadoDisponibilidad(EstadoDisponibilidad estadoDisponibilidad) {
		this.estadoDisponibilidad = estadoDisponibilidad;
	}

	public List<Publicacion> getPublicaciones() {
		return publicaciones;
	}

	public void setPublicaciones(List<Publicacion> publicaciones) {
		this.publicaciones = publicaciones;
	}
}