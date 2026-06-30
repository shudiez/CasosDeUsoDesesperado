package com.desi.inmobiliaria.service;

import java.util.List;

import com.desi.inmobiliaria.entity.Ciudad;

public interface CiudadService {

	// Devuelve todas las ciudades
	List<Ciudad> listarTodas();

	// Busca una ciudad por su id
	Ciudad buscarPorId(Long id);

	// Guarda una ciudad nueva o editada
	Ciudad guardar(Ciudad ciudad);

	// Elimina una ciudad
	void eliminar(Long id);
}
