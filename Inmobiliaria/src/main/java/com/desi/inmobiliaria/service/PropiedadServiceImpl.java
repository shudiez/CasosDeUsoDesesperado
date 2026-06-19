package com.desi.inmobiliaria.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.HistorialEstadoPropiedad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;
import com.desi.inmobiliaria.repository.ContratoRepository;
import com.desi.inmobiliaria.repository.HistorialEstadoPropiedadRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;
import excepciones.EntidadNoEncontradaException;
import excepciones.Excepcion;

@Service
public class PropiedadServiceImpl implements PropiedadService {

	@Autowired
	private PropiedadRepository propiedadRepository;

	@Autowired
	private HistorialEstadoPropiedadRepository historialEstadoPropiedadRepository;

	@Autowired
	private ContratoRepository contratoRepository;

	@Override
	public Propiedad guardar(Propiedad propiedad) throws Excepcion {

		if (propiedad.getDireccion() == null || propiedad.getDireccion().isBlank()) {
			throw new Excepcion("La dirección es obligatoria");
		}

		if (propiedad.getDescripcion() == null || propiedad.getDescripcion().isBlank()) {
			throw new Excepcion("La descripción es obligatoria");
		}

		if (propiedad.getCantidadAmbientes() == null || propiedad.getCantidadAmbientes() <= 0) {
			throw new Excepcion("La cantidad de ambientes debe ser mayor a cero");
		}

		if (propiedad.getMetrosCuadrados() == null || propiedad.getMetrosCuadrados() <= 0) {
			throw new Excepcion("Los metros cuadrados deben ser mayores a cero");
		}

		if (propiedad.getEstadoDisponibilidad() == null) {
			propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.DISPONIBLE);
		}

		Propiedad propiedadExistente = propiedadRepository.findByDireccionAndCiudad(propiedad.getDireccion(),
				propiedad.getCiudad());

		if (propiedadExistente != null && !propiedadExistente.getId().equals(propiedad.getId())) {
			throw new Excepcion("Ya existe una propiedad con esa dirección y ciudad");
		}

		if (propiedad.getId() != null
				&& (propiedad.getEstadoDisponibilidad() == EstadoDisponibilidad.DISPONIBLE
						|| propiedad.getEstadoDisponibilidad() == EstadoDisponibilidad.INACTIVA)
				&& contratoRepository.existsByPropiedadAndEstadoAndEliminadoFalse(propiedad, EstadoContrato.ACTIVO)) {

			throw new Excepcion("No se puede cambiar el estado porque existe un contrato activo");
		}

		Propiedad propiedadGuardada = propiedadRepository.save(propiedad);

		HistorialEstadoPropiedad historial = new HistorialEstadoPropiedad();
		historial.setPropiedad(propiedadGuardada);
		historial.setEstado(propiedadGuardada.getEstadoDisponibilidad());
		historial.setFechaHora(LocalDateTime.now());

		historialEstadoPropiedadRepository.save(historial);

		return propiedadGuardada;
	}

	@Override
	public List<Propiedad> listarTodas() {
		return propiedadRepository.findByEliminadaFalse();
	}

	@Override
	public Propiedad buscarPorId(Long id) {
		return propiedadRepository.findById(id).orElseThrow(() -> new EntidadNoEncontradaException("la propiedad", id));
	}

	@Override
	public void eliminar(Long id) throws Excepcion {

		Propiedad propiedad = buscarPorId(id);

		if (contratoRepository.existsByPropiedadAndEstadoAndEliminadoFalse(propiedad, EstadoContrato.ACTIVO)) {
			throw new Excepcion("No se puede eliminar la propiedad porque tiene un contrato activo vigente");
		}

		propiedad.setEliminada(true);

		propiedadRepository.save(propiedad);
	}

	@Override
	public List<Propiedad> buscarPorDireccion(String direccion) {
		return propiedadRepository.findByDireccionContainingIgnoreCaseAndEliminadaFalse(direccion);
	}

	@Override
	public List<Propiedad> buscarPorCiudad(Long ciudadId) {
		return propiedadRepository.findByCiudadIdAndEliminadaFalse(ciudadId);
	}

	@Override
	public List<Propiedad> buscarPorTipo(TipoPropiedad tipo) {
		return propiedadRepository.findByTipoAndEliminadaFalse(tipo);
	}

	@Override
	public List<Propiedad> buscarPorEstado(EstadoDisponibilidad estado) {
		return propiedadRepository.findByEstadoDisponibilidadAndEliminadaFalse(estado);
	}
}