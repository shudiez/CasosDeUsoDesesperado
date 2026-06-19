package com.desi.inmobiliaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.repository.CiudadRepository;

@Service
// Se encarga de la lógica relacionada con las ciudades
public class CiudadService {

	// Spring inyecta automáticamente el repositorio
	@Autowired
	private CiudadRepository ciudadRepository;

	// Devuelve todas las ciudades cargadas
	public List<Ciudad> listarTodas() {
		return ciudadRepository.findAll();
	}
}