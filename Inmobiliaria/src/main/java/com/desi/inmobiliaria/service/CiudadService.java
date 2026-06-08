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

	//LISTAR CIUDADES
	public List<Ciudad> listarTodas() {
		return ciudadRepository.findAll();
	}
}