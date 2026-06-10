package com.desi.inmobiliaria.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.HistorialEstadoPropiedad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.repository.ContratoRepository;
import com.desi.inmobiliaria.repository.HistorialEstadoPropiedadRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;

@Service
//Acá se manejan las reglas de negocio de las propiedades
public class PropiedadService {

	// Permite acceder a los datos de las propiedades
	@Autowired
	private PropiedadRepository propiedadRepository;

	// Permite guardar el historial de cambios de estado
	@Autowired
	private HistorialEstadoPropiedadRepository historialEstadoPropiedadRepository;

	@Autowired
	private ContratoRepository contratoRepository;

	// GUARDAR PROPIEDAD EN LA BD realizando las validaciones necesarias
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

		// Si no se indicó un estado, se asigna Disponible por defecto
		if (propiedad.getEstadoDisponibilidad() == null) {
			propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.DISPONIBLE);
		}

		// Verifico que no exista otra propiedad con la misma dirección y ciudad
		Propiedad propiedadExistente = propiedadRepository.findByDireccionAndCiudad(propiedad.getDireccion(),
				propiedad.getCiudad());

		if (propiedadExistente != null && !propiedadExistente.getId().equals(propiedad.getId())) {

			throw new RuntimeException("Ya existe una propiedad con esa dirección y ciudad");
		}

		if (propiedad.getId() != null
				&& (propiedad.getEstadoDisponibilidad() == EstadoDisponibilidad.DISPONIBLE
						|| propiedad.getEstadoDisponibilidad() == EstadoDisponibilidad.INACTIVA)
				&& contratoRepository.existsByPropiedadAndEstadoAndEliminadoFalse(propiedad, EstadoContrato.ACTIVO)) {

			throw new RuntimeException("No se puede cambiar el estado porque existe un contrato activo");
		}

		// Guarda la propiedad en la base de datos
		Propiedad propiedadGuardada = propiedadRepository.save(propiedad);

		// Guarda un registro en el historial con el estado actual de la propiedad
		HistorialEstadoPropiedad historial = new HistorialEstadoPropiedad();
		historial.setPropiedad(propiedadGuardada);
		historial.setEstado(propiedadGuardada.getEstadoDisponibilidad());
		historial.setFechaHora(LocalDateTime.now());

		historialEstadoPropiedadRepository.save(historial);

		return propiedadGuardada;
	}

	// LISTAR PROPIEDADES
	// Devuelve solamente las propiedades que no fueron eliminadas
	public List<Propiedad> listarTodas() {
		return propiedadRepository.findByEliminadaFalse();
	}

	// EDITAR PROPIEDAD
	// Busca una propiedad por su ID para mostrarla o editarla
	public Propiedad buscarPorId(Long id) {
		return propiedadRepository.findById(id).orElse(null);
	}

	// ELIMINAR PROPIEDAD
	public void eliminar(Long id) {

		Propiedad propiedad = buscarPorId(id);

		if (contratoRepository.existsByPropiedadAndEstadoAndEliminadoFalse(propiedad, EstadoContrato.ACTIVO)) {

			throw new RuntimeException("No se puede eliminar la propiedad porque tiene un contrato activo vigente");
		}

		propiedad.setEliminada(true);

		propiedadRepository.save(propiedad);
	}
}