package com.desi.inmobiliaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.Persona;
import com.desi.inmobiliaria.repository.PersonaRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;

@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private PropiedadRepository propiedadRepository;

	// METODO LISTAR PERSONA
	@Override
	public List<Persona> listarTodas() {
		return personaRepository.findAll();
	}

	// METODO BUSCAR PERSONBA
	@Override
	public Persona buscarPorId(Long id) {
		return personaRepository.findById(id).orElse(null);
	}

	// METODO GUARDAR PERSONA
	@Override
	public Persona guardar(Persona persona) {

		if (persona.getNombre() == null || persona.getNombre().isBlank()) {
			throw new RuntimeException("El nombre es obligatorio");
		}

		if (persona.getApellido() == null || persona.getApellido().isBlank()) {
			throw new RuntimeException("El apellido es obligatorio");
		}

		if (persona.getDniCuit() == null || persona.getDniCuit().isBlank()) {
			throw new RuntimeException("El DNI/CUIT es obligatorio");
		}

		if (persona.getId() == null) {

			if (personaRepository.findByDniCuit(persona.getDniCuit()).isPresent()) {
				throw new RuntimeException("Ya existe una persona con ese DNI/CUIT");
			}

		} else {

			if (personaRepository.existsByDniCuitAndIdNot(persona.getDniCuit(), persona.getId())) {

				throw new RuntimeException("Ya existe una persona con ese DNI/CUIT");
			}
		}

		return personaRepository.save(persona);
	}

	// METODO ELIMAR PERSONA
	@Override
	public void eliminar(Long id) {

		if (propiedadRepository.existsByPropietarioId(id)) {

			throw new RuntimeException("No se puede eliminar la persona porque está asociada a una propiedad");
		}

		personaRepository.deleteById(id);
	}
}