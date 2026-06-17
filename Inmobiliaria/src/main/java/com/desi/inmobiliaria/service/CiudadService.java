package com.desi.inmobiliaria.service;

import java.util.List;

import com.desi.inmobiliaria.entity.Ciudad;

public interface CiudadService {

	List<Ciudad> listarTodas();

}

/*
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
*/
