package com.desi.inmobiliaria.service;

import org.springframework.stereotype.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.repository.CiudadRepository;

@Service
//Se encarga de la lógica relacionada con las ciudades
public class CiudadService {

	// Spring inyecta automáticamente el repositorio para poder acceder a las
	// ciudades
	@Autowired
	private CiudadRepository ciudadRepository;

	// Devuelve todas las ciudades guardadas en la base de datos
	public List<Ciudad> listarTodas() {
		return ciudadRepository.findAll();
	}

}
