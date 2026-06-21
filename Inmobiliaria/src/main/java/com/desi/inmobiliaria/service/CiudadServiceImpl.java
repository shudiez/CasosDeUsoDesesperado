package com.desi.inmobiliaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.repository.CiudadRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;

@Service
public class CiudadServiceImpl implements CiudadService {

	@Autowired
	private CiudadRepository ciudadRepository;

	@Autowired
	private PropiedadRepository propiedadRepository;

	// METODO LISTAR CIUDADES
	@Override
	public List<Ciudad> listarTodas() {
		return ciudadRepository.findAll();
	}

	// METODO BUSCAR CIUDAD
	@Override
	public Ciudad buscarPorId(Long id) {
		return ciudadRepository.findById(id).orElse(null);
	}

	// METODO GUARDAR CIUDAD
	@Override
	public Ciudad guardar(Ciudad ciudad) {

		if (ciudad.getNombre() == null || ciudad.getNombre().isBlank()) {
			throw new RuntimeException("El nombre de la ciudad es obligatorio");
		}

		if (ciudad.getId() == null) {

			if (ciudadRepository.findByNombre(ciudad.getNombre()).isPresent()) {

				throw new RuntimeException("Ya existe una ciudad con ese nombre");
			}

		} else {

			if (ciudadRepository.existsByNombreAndIdNot(ciudad.getNombre(), ciudad.getId())) {

				throw new RuntimeException("Ya existe una ciudad con ese nombre");
			}
		}

		if (ciudad.getProvincia() == null || ciudad.getProvincia().getId() == null) {

			throw new RuntimeException("La provincia es obligatoria");
		}

		return ciudadRepository.save(ciudad);
	}

	// METODO ELIMINAR CIUDAD
	@Override
	public void eliminar(Long id) {

		if (propiedadRepository.existsByCiudadId(id)) {

			throw new RuntimeException("No se puede eliminar la ciudad porque tiene propiedades asociadas");
		}

		ciudadRepository.deleteById(id);
	}

}