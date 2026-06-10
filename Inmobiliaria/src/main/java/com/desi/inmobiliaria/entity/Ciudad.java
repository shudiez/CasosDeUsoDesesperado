package com.desi.inmobiliaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
//Guarda las ciudades que se usan en el sistema
public class Ciudad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// ID único de la ciudad
	private Long id;

	private String nombre;

	// Indica a qué provincia pertenece la ciudad
	@ManyToOne
	private Provincia provincia;

	// Constructor vacio
	public Ciudad() {

	}

	// Getters and setters
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

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

}
