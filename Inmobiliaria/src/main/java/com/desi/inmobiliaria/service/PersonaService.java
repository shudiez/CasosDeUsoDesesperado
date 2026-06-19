package com.desi.inmobiliaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.Persona;
import com.desi.inmobiliaria.repository.PersonaRepository;

@Service
// Servicio que maneja las personas
public class PersonaService {

	// Conecta este servicio con el repositorio de personas
	@Autowired
	private PersonaRepository personaRepository;

	// Trae todas las personas cargadas en el sistema
	public List<Persona> listarTodas() {
		return personaRepository.findAll();
	}
}