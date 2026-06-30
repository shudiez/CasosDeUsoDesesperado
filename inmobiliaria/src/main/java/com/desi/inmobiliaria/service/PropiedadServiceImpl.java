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

	// METODO GUARDAR PROPIEDAD
	@Override
	public Propiedad guardar(Propiedad propiedad) throws Excepcion {

		// Direccion obligatoria
		if (propiedad.getDireccion() == null || propiedad.getDireccion().isBlank()) {
			throw new Excepcion("La dirección es obligatoria");
		}

		// Descripcion obligatoria
		if (propiedad.getDescripcion() == null || propiedad.getDescripcion().isBlank()) {
			throw new Excepcion("La descripción es obligatoria");
		}

		// Comodidades obligatorias
		if (propiedad.getComodidades() == null || propiedad.getComodidades().isBlank()) {
			throw new Excepcion("Las comodidades son obligatorias");
		}
		
		// Ambientes mayores a 0
		if (propiedad.getCantidadAmbientes() == null || propiedad.getCantidadAmbientes() <= 0) {
			throw new Excepcion("La cantidad de ambientes debe ser mayor a cero");
		}

		// Metros cuadrados mayores a 0
		if (propiedad.getMetrosCuadrados() == null || propiedad.getMetrosCuadrados() <= 0) {
			throw new Excepcion("Los metros cuadrados deben ser mayores a cero");
		}

		// Estado por defecto Disponible
		if (propiedad.getEstadoDisponibilidad() == null) {
			propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.DISPONIBLE);
		}

		// No repetir direccion y ciudad
		Propiedad propiedadExistente = propiedadRepository.findByDireccionAndCiudad(propiedad.getDireccion(),
				propiedad.getCiudad());

		if (propiedadExistente != null && !propiedadExistente.getId().equals(propiedad.getId())) {
			throw new Excepcion("Ya existe una propiedad con esa dirección y ciudad");
		}

		// No cambair el esyado si tiene un contrato activo
		// No eliminar propiedades con contratoa ctivo
		if (propiedad.getId() != null
				&& (propiedad.getEstadoDisponibilidad() == EstadoDisponibilidad.DISPONIBLE
						|| propiedad.getEstadoDisponibilidad() == EstadoDisponibilidad.INACTIVA)
				&& contratoRepository.existsByPropiedadAndEstadoAndEliminadoFalse(propiedad, EstadoContrato.ACTIVO)) {

			throw new Excepcion("No se puede cambiar el estado porque existe un contrato activo");
		}

		if (propiedad.getCiudad() == null) {
			throw new Excepcion("La ciudad es obligatoria");
		}

		if (propiedad.getPropietario() == null) {
			throw new Excepcion("El propietario es obligatorio");
		}

		if (propiedad.getTipo() == null) {
			throw new Excepcion("Debe seleccionar un tipo de propiedad");
		}

		Propiedad propiedadGuardada = propiedadRepository.save(propiedad);

		// Historia de estados
		HistorialEstadoPropiedad historial = new HistorialEstadoPropiedad();
		historial.setPropiedad(propiedadGuardada);
		historial.setEstado(propiedadGuardada.getEstadoDisponibilidad());
		historial.setFechaHora(LocalDateTime.now());

		historialEstadoPropiedadRepository.save(historial);

		return propiedadGuardada;
	}

	// METODO LISTAR PROPIEDADES
	@Override
	public List<Propiedad> listarTodas() {
		return propiedadRepository.findByEliminadaFalse();
	}

	// METODO BUSCAR PROPIEDAD
	@Override
	public Propiedad buscarPorId(Long id) {
		return propiedadRepository.findById(id).orElseThrow(() -> new EntidadNoEncontradaException("la propiedad", id));
	}

	// METODO ELIMINAR PROPIEDAD
	@Override
	public void eliminar(Long id) throws Excepcion {

		Propiedad propiedad = buscarPorId(id);

		if (contratoRepository.existsByPropiedadAndEstadoAndEliminadoFalse(propiedad, EstadoContrato.ACTIVO)) {
			throw new Excepcion("No se puede eliminar la propiedad porque tiene un contrato activo vigente");
		}

		propiedad.setEliminada(true);

		propiedadRepository.save(propiedad);
	}

	// METODO BUSCAR PROPIEDAD POR DIRECCION
	@Override
	public List<Propiedad> buscarPorDireccion(String direccion) {
		return propiedadRepository.findByDireccionContainingIgnoreCaseAndEliminadaFalse(direccion);
	}

	// METODO BUSCAR PROPIEDAD POR CIUDAD
	@Override
	public List<Propiedad> buscarPorCiudad(Long ciudadId) {
		return propiedadRepository.findByCiudadIdAndEliminadaFalse(ciudadId);
	}

	// METODO BUSCAR PROPIEDAD POR TIPO
	@Override
	public List<Propiedad> buscarPorTipo(TipoPropiedad tipo) {
		return propiedadRepository.findByTipoAndEliminadaFalse(tipo);
	}

	// METODO BUSCAR PROPIEDAD POR ESTADO
	@Override
	public List<Propiedad> buscarPorEstado(EstadoDisponibilidad estado) {
		return propiedadRepository.findByEstadoDisponibilidadAndEliminadaFalse(estado);
	}

	// METODO BUSCAR PROPIEDADES CON FILTROS
	@Override
	public List<Propiedad> buscarConFiltros(String direccion, Long ciudadId, TipoPropiedad tipo,
			EstadoDisponibilidad estado) {

		return propiedadRepository.buscarConFiltros(direccion, ciudadId, tipo, estado);
	}
}