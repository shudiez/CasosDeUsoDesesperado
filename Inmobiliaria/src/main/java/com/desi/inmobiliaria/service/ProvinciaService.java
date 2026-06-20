package com.desi.inmobiliaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.Provincia;
import com.desi.inmobiliaria.repository.ProvinciaRepository;

@Service
public class ProvinciaService {

	@Autowired
	private ProvinciaRepository provinciaRepository;

	public List<Provincia> listarTodas() {
		return provinciaRepository.findAll();
	}

	public Provincia buscarPorId(Long id) {
		return provinciaRepository.findById(id).orElse(null);
	}

	public Provincia guardar(Provincia provincia) {
		return provinciaRepository.save(provincia);
	}

	public void eliminar(Long id) {
		provinciaRepository.deleteById(id);
	}
}