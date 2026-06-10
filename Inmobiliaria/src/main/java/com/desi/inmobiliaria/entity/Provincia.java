package com.desi.inmobiliaria.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
//Guarda las provincias que se van a usar en el sistema
public class Provincia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// ID único de la provincia
	private Long id;

	private String nombre;

	// Constructor vacio
	public Provincia() {

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

}
