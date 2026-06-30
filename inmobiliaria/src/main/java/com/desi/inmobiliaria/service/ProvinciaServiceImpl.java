package com.desi.inmobiliaria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.Provincia;
import com.desi.inmobiliaria.repository.CiudadRepository;
import com.desi.inmobiliaria.repository.ProvinciaRepository;

@Service
public class ProvinciaServiceImpl implements ProvinciaService {

	@Autowired
	private ProvinciaRepository provinciaRepository;

	@Autowired
	private CiudadRepository ciudadRepository;

	// METODO LISTAR PROVINCIAS
	public List<Provincia> listarTodas() {
		return provinciaRepository.findAll();
	}

	// METODO BUSCAR PROVINCIA
	public Provincia buscarPorId(Long id) {
		return provinciaRepository.findById(id).orElse(null);
	}

	// METODO GUARDAR PROVINCIA
	@Override
	public Provincia guardar(Provincia provincia) {

		if (provincia.getNombre() == null || provincia.getNombre().isBlank()) {
			throw new RuntimeException("El nombre de la provincia es obligatorio");
		}

		if (provincia.getId() == null) {

			if (provinciaRepository.findByNombre(provincia.getNombre()).isPresent()) {
				throw new RuntimeException("Ya existe una provincia con ese nombre");
			}

		} else {

			if (provinciaRepository.existsByNombreAndIdNot(provincia.getNombre(), provincia.getId())) {

				throw new RuntimeException("Ya existe una provincia con ese nombre");
			}
		}

		return provinciaRepository.save(provincia);
	}

	// METODO ELIMINAR PROVINCIA
	@Override
	public void eliminar(Long id) {

		if (ciudadRepository.existsByProvinciaId(id)) {

			throw new RuntimeException("No se puede eliminar la provincia porque tiene ciudades asociadas");
		}

		provinciaRepository.deleteById(id);
	}
}