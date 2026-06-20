package com.desi.inmobiliaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.repository.CiudadRepository;

@Service
public class CiudadService {

	@Autowired
	private CiudadRepository ciudadRepository;

	public List<Ciudad> listarTodas() {
		return ciudadRepository.findAll();
	}

	public Ciudad buscarPorId(Long id) {
		return ciudadRepository.findById(id).orElse(null);
	}

	public Ciudad guardar(Ciudad ciudad) {

		if (ciudad.getNombre() == null || ciudad.getNombre().isBlank()) {
			throw new RuntimeException("El nombre de la ciudad es obligatorio");
		}

		return ciudadRepository.save(ciudad);
	}

	public void eliminar(Long id) {
		ciudadRepository.deleteById(id);
	}
}
