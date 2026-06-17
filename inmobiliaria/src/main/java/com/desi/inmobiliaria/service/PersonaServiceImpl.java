package com.desi.inmobiliaria.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.desi.inmobiliaria.entity.Persona;
import com.desi.inmobiliaria.repository.PersonaRepository;

@Service
public class PersonaServiceImpl implements PersonaService {

	private final PersonaRepository personaRepository;

	public PersonaServiceImpl(PersonaRepository personaRepository) {
		this.personaRepository = personaRepository;
	}

	@Override
	public List<Persona> listarTodas() {

		return personaRepository.findAll();
	}

	@Override
	public void guardar(Persona persona) {
		personaRepository.save(persona);

	}

	@Override
	public Persona buscarPorId(Long id) {
		return personaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Persona no encontrada con el ID:" + id));
	}

	@Override
	public void eliminar(Long id) {
		personaRepository.deleteById(id);

	}
}
