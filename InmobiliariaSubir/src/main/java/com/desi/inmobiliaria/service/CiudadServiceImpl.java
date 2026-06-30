package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.repository.CiudadRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiudadServiceImpl implements CiudadService {
	@Autowired
	private CiudadRepository ciudadRepository;
	@Autowired
	private PropiedadRepository propiedadRepository;

	public CiudadServiceImpl() {
	}

	@Override
	public List<Ciudad> listarTodas() {
		return this.ciudadRepository.findAll();
	}

	@Override
	public Ciudad buscarPorId(Long id) {
		return ciudadRepository.findById(id).orElse(null);
	}

	@Override
	public Ciudad guardar(Ciudad ciudad) {
		if (ciudad.getNombre() != null && !ciudad.getNombre().isBlank()) {
			if (ciudad.getId() == null) {
				if (this.ciudadRepository.findByNombre(ciudad.getNombre()).isPresent()) {
					throw new RuntimeException("Ya existe una ciudad con ese nombre");
				}
			} else if (this.ciudadRepository.existsByNombreAndIdNot(ciudad.getNombre(), ciudad.getId())) {
				throw new RuntimeException("Ya existe una ciudad con ese nombre");
			}

			if (ciudad.getProvincia() != null && ciudad.getProvincia().getId() != null) {
				return (Ciudad) this.ciudadRepository.save(ciudad);
			} else {
				throw new RuntimeException("La provincia es obligatoria");
			}
		} else {
			throw new RuntimeException("El nombre de la ciudad es obligatorio");
		}
	}

	@Override
	public void eliminar(Long id) {
		if (this.propiedadRepository.existsByCiudadId(id)) {
			throw new RuntimeException("No se puede eliminar la ciudad porque tiene propiedades asociadas");
		} else {
			this.ciudadRepository.deleteById(id);
		}
	}
}
