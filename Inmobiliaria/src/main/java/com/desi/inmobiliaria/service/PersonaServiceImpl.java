package com.desi.inmobiliaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.Persona;
import com.desi.inmobiliaria.repository.PersonaRepository;

@Service
public class PersonaServiceImpl implements PersonaService {

	@Autowired
	private PersonaRepository personaRepository;

	@Override
	public List<Persona> listarTodas() {
		return personaRepository.findAll();
	}
}