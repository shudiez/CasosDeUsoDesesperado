package com.desi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Propiedad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String direccion;
	private Integer cantidadAmbientes;
	private Double metrosCuadrados;
	private String descripcion;
	private String comodidades;
	private boolean eliminada;

	@ManyToOne
	private Persona propietario;

	@ManyToOne
	private Ciudad ciudad;

	@Enumerated(EnumType.STRING)
	private TipoPropiedad tipo;

	@Enumerated(EnumType.STRING)
	private EstadoDisponibilidad estadoDisponibilidad;

	public Propiedad() {
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

	public Persona getPropietario() {
		return propietario;
	}

	public void setPropietario(Persona propietario) {
		this.propietario = propietario;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
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

}