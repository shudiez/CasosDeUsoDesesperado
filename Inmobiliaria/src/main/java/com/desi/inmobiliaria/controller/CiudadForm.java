package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.entity.Provincia;

public class CiudadForm {

	private Long id;

	private String nombre;

	private Long provinciaId;

	public CiudadForm() {
		super();
	}

	public CiudadForm(Ciudad c) {
		this.id = c.getId();
		this.nombre = c.getNombre();

		if (c.getProvincia() != null) {
			this.provinciaId = c.getProvincia().getId();
		}
	}

	public static CiudadForm fromPojo(Ciudad c) {

		CiudadForm form = new CiudadForm();

		form.setId(c.getId());
		form.setNombre(c.getNombre());

		if (c.getProvincia() != null) {
			form.setProvinciaId(c.getProvincia().getId());
		}

		return form;
	}

	public Ciudad toPojo() {

		Ciudad ciudad = new Ciudad();

		ciudad.setId(this.id);
		ciudad.setNombre(this.nombre);

		if (this.provinciaId != null) {

			Provincia provincia = new Provincia();
			provincia.setId(this.provinciaId);

			ciudad.setProvincia(provincia);
		}

		return ciudad;
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

	public Long getProvinciaId() {
		return provinciaId;
	}

	public void setProvinciaId(Long provinciaId) {
		this.provinciaId = provinciaId;
	}
}