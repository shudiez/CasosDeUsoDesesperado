package com.desi.inmobiliaria.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.EstadoIncidente;
import com.desi.inmobiliaria.entity.HistorialEstadoIncidente;
import com.desi.inmobiliaria.entity.Incidente;
import com.desi.inmobiliaria.repository.HistorialEstadoIncidenteRepository;
import com.desi.inmobiliaria.repository.IncidenteRepository;

@Service
public class IncidenteServiceImpl implements IncidenteService {

	@Autowired
	private IncidenteRepository incidenteRepository;

	@Autowired
	private HistorialEstadoIncidenteRepository historialRepository;

	@Override
	public List<Incidente> listarTodos() {
		return incidenteRepository.findByEliminadoFalse();
	}

	@Override
	public Incidente buscarPorId(Long id) {
		return incidenteRepository.findById(id).orElse(null);
	}

	@Override
	public Incidente guardar(Incidente incidente) {

		// Validaciones

		if (incidente.getTitulo() == null || incidente.getTitulo().isBlank()) {
			throw new RuntimeException("El título es obligatorio");
		}

		if (incidente.getDescripcion() == null || incidente.getDescripcion().isBlank()) {
			throw new RuntimeException("La descripción es obligatoria");
		}

		if (incidente.getCategoria() == null) {
			throw new RuntimeException("Debe seleccionar una categoría");
		}

		if (incidente.getPrioridad() == null) {
			throw new RuntimeException("Debe seleccionar una prioridad");
		}

		if (incidente.getPropiedad() == null) {
			throw new RuntimeException("Debe seleccionar una propiedad");
		}

		// Estado por defecto

		if (incidente.getEstado() == null) {
			incidente.setEstado(EstadoIncidente.ABIERTO);
		}

		// Fecha de alta automática

		if (incidente.getFechaAlta() == null) {
			incidente.setFechaAlta(LocalDateTime.now());
		}

		// Guarda el incidente

		Incidente incidenteGuardado = incidenteRepository.save(incidente);

		// Guarda el historial

		HistorialEstadoIncidente historial = new HistorialEstadoIncidente();
		historial.setIncidente(incidenteGuardado);
		historial.setEstado(incidenteGuardado.getEstado());
		historial.setFechaHora(LocalDateTime.now());

		historialRepository.save(historial);

		return incidenteGuardado;
	}

	@Override
	public void eliminar(Long id) {

		Incidente incidente = buscarPorId(id);

		incidente.setEliminado(true);

		incidenteRepository.save(incidente);

	}

}