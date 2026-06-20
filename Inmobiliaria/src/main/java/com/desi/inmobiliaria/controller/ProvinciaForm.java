package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Provincia;

import jakarta.validation.constraints.NotBlank;

public class ProvinciaForm {

	private Long id;

	@NotBlank(message = "El nombre de la provincia es obligatorio")
	private String nombre;

	public ProvinciaForm() {
		super();
	}

	public ProvinciaForm(Provincia provincia) {
		this.id = provincia.getId();
		this.nombre = provincia.getNombre();
	}

	public static ProvinciaForm fromPojo(Provincia provincia) {
		return new ProvinciaForm(provincia);
	}

	public Provincia toPojo() {

		Provincia provincia = new Provincia();

		provincia.setId(this.id);
		provincia.setNombre(this.nombre);

		return provincia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}