package com.desi.inmobiliaria.service;

import java.util.List;

import com.desi.inmobiliaria.entity.Persona;

public interface PersonaService {

	// METODO LISTAR PERSONAS
	List<Persona> listarTodas();

	// METODO BUSCAR PERSONA
	Persona buscarPorId(Long id);

	// METODO GUARDAR PERSONA
	Persona guardar(Persona persona);

	// METODO ELIMINAR PERSONA
	void eliminar(Long id);
}