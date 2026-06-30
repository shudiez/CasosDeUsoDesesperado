package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.TipoPropiedad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.entity.Persona;
import com.desi.inmobiliaria.entity.Propiedad;

public class PropiedadForm {

	private Long id;

	@NotBlank(message = "La dirección es obligatoria")
	private String direccion;

	@NotNull(message = "La cantidad de ambientes es obligatoria")
	@Positive(message = "La cantidad de ambientes debe ser mayor a cero")
	private Integer cantidadAmbientes;

	@NotNull(message = "Los metros cuadrados son obligatorios")
	@Positive(message = "Los metros cuadrados deben ser mayores a cero")
	private Double metrosCuadrados;

	@NotBlank(message = "La descripción es obligatoria")
	private String descripcion;

	@NotBlank(message = "Las comodidades son obligatorias")
	private String comodidades;
	

	@NotNull(message = "Debe seleccionar una ciudad")
	private Long ciudadId;

	@NotNull(message = "Debe seleccionar un propietario")
	private Long propietarioId;

	@NotNull(message = "Debe seleccionar un tipo de propiedad")
	private TipoPropiedad tipo;

	private EstadoDisponibilidad estadoDisponibilidad;

	public PropiedadForm() {
		super();
	}

	public PropiedadForm(Propiedad p) {
		super();

		this.id = p.getId();
		this.direccion = p.getDireccion();
		this.cantidadAmbientes = p.getCantidadAmbientes();
		this.metrosCuadrados = p.getMetrosCuadrados();
		this.descripcion = p.getDescripcion();
		this.comodidades = p.getComodidades();
		this.tipo = p.getTipo();
		this.estadoDisponibilidad = p.getEstadoDisponibilidad();

		if (p.getCiudad() != null) {
			this.ciudadId = p.getCiudad().getId();
		}

		if (p.getPropietario() != null) {
			this.propietarioId = p.getPropietario().getId();
		}
	}

	public static PropiedadForm fromPojo(Propiedad p) {

		PropiedadForm form = new PropiedadForm();

		form.setId(p.getId());
		form.setDireccion(p.getDireccion());
		form.setCantidadAmbientes(p.getCantidadAmbientes());
		form.setMetrosCuadrados(p.getMetrosCuadrados());
		form.setDescripcion(p.getDescripcion());
		form.setComodidades(p.getComodidades());
		form.setTipo(p.getTipo());
		form.setEstadoDisponibilidad(p.getEstadoDisponibilidad());

		if (p.getCiudad() != null) {
			form.setCiudadId(p.getCiudad().getId());
		}

		if (p.getPropietario() != null) {
			form.setPropietarioId(p.getPropietario().getId());
		}

		return form;
	}

	public Propiedad toPojo() {

		Propiedad p = new Propiedad();

		p.setId(this.id);
		p.setDireccion(this.direccion);
		p.setCantidadAmbientes(this.cantidadAmbientes);
		p.setMetrosCuadrados(this.metrosCuadrados);
		p.setDescripcion(this.descripcion);
		p.setComodidades(this.comodidades);
		p.setTipo(this.tipo);
		p.setEstadoDisponibilidad(this.estadoDisponibilidad);

		if (this.ciudadId != null) {
			Ciudad ciudad = new Ciudad();
			ciudad.setId(this.ciudadId);
			p.setCiudad(ciudad);
		}

		if (this.propietarioId != null) {
			Persona propietario = new Persona();
			propietario.setId(this.propietarioId);
			p.setPropietario(propietario);
		}

		return p;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getComodidades() {
		return comodidades;
	}

	public void setComodidades(String comodidades) {
		this.comodidades = comodidades;
	}

	public Long getCiudadId() {
		return ciudadId;
	}

	public void setCiudadId(Long ciudadId) {
		this.ciudadId = ciudadId;
	}

	public Long getPropietarioId() {
		return propietarioId;
	}

	public void setPropietarioId(Long propietarioId) {
		this.propietarioId = propietarioId;
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