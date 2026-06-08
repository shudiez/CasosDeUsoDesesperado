package com.desi.inmobiliaria.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.HistorialEstadoPropiedad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.repository.HistorialEstadoPropiedadRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;

@Service
public class PropiedadService {

	@Autowired
	private PropiedadRepository propiedadRepository;

	@Autowired
	private HistorialEstadoPropiedadRepository historialEstadoPropiedadRepository;

	// GUARDAR PROPIEDAD EN LA BD
	public Propiedad guardar(Propiedad propiedad) {

		// Verifico si la direccion esta cargada o vacia
		if (propiedad.getDireccion() == null || propiedad.getDireccion().isBlank()) {
			throw new RuntimeException("La dirección es obligatoria");
		}

		// Verifico si la descripcion esta cargada o vacia
		if (propiedad.getDescripcion() == null || propiedad.getDescripcion().isBlank()) {
			throw new RuntimeException("La descripción es obligatoria");
		}

		// Verifico si la cantidad de ambientes esta vacia o si es menor o igual a 0
		if (propiedad.getCantidadAmbientes() == null || propiedad.getCantidadAmbientes() <= 0) {
			throw new RuntimeException("La cantidad de ambientes debe ser mayor a cero");
		}

		// Verifico si los metros cuadrados esten cargados y son mayorores a cero
		if (propiedad.getMetrosCuadrados() == null || propiedad.getMetrosCuadrados() <= 0) {
			throw new RuntimeException("Los metros cuadrados deben ser mayores a cero");
		}

		// verifico si es estado de la propiedad es disponible
		if (propiedad.getEstadoDisponibilidad() == null) {
			propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.DISPONIBLE);
		}

		// Verifico si ya existe la misma direccion y ciudad
		if (propiedad.getId() == null
				&& propiedadRepository.existsByDireccionAndCiudad(propiedad.getDireccion(), propiedad.getCiudad())) {

			throw new RuntimeException("Ya existe una propiedad con esa dirección y ciudad");
		}

		// si esta todo ok, lo guarda y lo carga
		//guarda registro de fechas de cada cambio de estado de la propiedad.
		Propiedad propiedadGuardada = propiedadRepository.save(propiedad);

		HistorialEstadoPropiedad historial = new HistorialEstadoPropiedad();
		historial.setPropiedad(propiedadGuardada);
		historial.setEstado(propiedadGuardada.getEstadoDisponibilidad());
		historial.setFechaHora(LocalDateTime.now());

		historialEstadoPropiedadRepository.save(historial);

		return propiedadGuardada;
	}

	// LISTAR PROPIEDADES
	public List<Propiedad> listarTodas() {
		return propiedadRepository.findAll();
	}

	// EDITAR PROPIEDAD
	public Propiedad buscarPorId(Long id) {
		return propiedadRepository.findById(id).orElse(null);
	}

	// ELIMINAR PROPIEDAD
	public void eliminar(Long id) {
		propiedadRepository.deleteById(id);
	}
}