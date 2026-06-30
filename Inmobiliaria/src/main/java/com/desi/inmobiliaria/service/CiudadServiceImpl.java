package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.repository.CiudadRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase contiene la lógica de negocio
public class CiudadServiceImpl implements CiudadService {

	// Repository para trabajar con las ciudades
	@Autowired
	private CiudadRepository ciudadRepository;

	// Lo uso para verificar si una ciudad tiene propiedades
	@Autowired
	private PropiedadRepository propiedadRepository;

	public CiudadServiceImpl() {
	}

	// Devuelve todas las ciudades
	@Override
	public List<Ciudad> listarTodas() {
		return ciudadRepository.findAll();
	}

	// Busca una ciudad por su id
	@Override
	public Ciudad buscarPorId(Long id) {
		return ciudadRepository.findById(id).orElse(null);
	}

	// Guarda una ciudad nueva o una editada
	@Override
	public Ciudad guardar(Ciudad ciudad) {

		// Verifico que el nombre no esté vacío
		if (ciudad.getNombre() != null && !ciudad.getNombre().isBlank()) {

			// Si es una ciudad nueva, verifico que no exista otra igual
			if (ciudad.getId() == null) {

				if (ciudadRepository.findByNombre(ciudad.getNombre()).isPresent()) {
					throw new RuntimeException("Ya existe una ciudad con ese nombre");
				}

			}
			// Si estoy editando, verifico que otra ciudad no tenga ese nombre
			else if (ciudadRepository.existsByNombreAndIdNot(ciudad.getNombre(), ciudad.getId())) {

				throw new RuntimeException("Ya existe una ciudad con ese nombre");
			}

			// Verifico que tenga una provincia seleccionada
			if (ciudad.getProvincia() != null && ciudad.getProvincia().getId() != null) {

				// Si está todo bien la guardo
				return ciudadRepository.save(ciudad);

			} else {

				throw new RuntimeException("La provincia es obligatoria");
			}

		} else {

			throw new RuntimeException("El nombre de la ciudad es obligatorio");
		}
	}

	// Elimina una ciudad
	@Override
	public void eliminar(Long id) {

		// Si la ciudad tiene propiedades asociadas no la dejo eliminar
		if (propiedadRepository.existsByCiudadId(id)) {

			throw new RuntimeException("No se puede eliminar la ciudad porque tiene propiedades asociadas");

		} else {

			// Si no tiene propiedades, la elimino
			ciudadRepository.deleteById(id);
		}
	}
}
