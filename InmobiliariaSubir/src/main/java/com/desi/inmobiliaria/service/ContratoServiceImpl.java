package com.desi.inmobiliaria.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.HistorialEstadoContrato;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.repository.ContratoRepository;
import com.desi.inmobiliaria.repository.HistorialEstadoContratoRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;

@Service
public class ContratoServiceImpl implements ContratoService {

	private final ContratoRepository contratoRepository;
	private final PropiedadRepository propiedadRepository;
	private final HistorialEstadoContratoRepository historialEstadoRepository;

	public ContratoServiceImpl(ContratoRepository contratoRepository,
			PropiedadRepository propiedadRepository,
			HistorialEstadoContratoRepository historialEstadoRepository) {

		this.contratoRepository = contratoRepository;
		this.propiedadRepository = propiedadRepository;
		this.historialEstadoRepository = historialEstadoRepository;
	}

	@Override
	public List<Contrato> listarTodos() {
		return contratoRepository.findAll();
	}

	@Override
	public List<Contrato> listarConFiltros(String propiedad, String inquilino,
			EstadoContrato estado, LocalDate fechaInicio) {

		String propFiltro = propiedad != null && !propiedad.isBlank() ? propiedad : null;
		String inqFiltro = inquilino != null && !inquilino.isBlank() ? inquilino : null;

		return contratoRepository.filtrarContratos(propFiltro, inqFiltro, estado, fechaInicio);
	}

	@Override
	public void guardar(Contrato contrato) {

		if (contrato.getDiaVencimientoMensual() == null
				|| contrato.getDiaVencimientoMensual() < 1
				|| contrato.getDiaVencimientoMensual() > 31) {
			throw new IllegalArgumentException("¡Error! El día de vencimiento mensual debe estar entre 1 y 31.");
		}

		if (contrato.getDuracionMeses() == null
				|| contrato.getDuracionMeses() < 1
				|| contrato.getDuracionMeses() > 36) {
			throw new IllegalArgumentException("¡Error! La duración del alquiler debe ser mayor a cero y menor de 36.");
		}

		if (contrato.getPropiedad() != null && contrato.getPropiedad().getId() != null) {
			Propiedad propiedadCompleta = propiedadRepository
					.findById(contrato.getPropiedad().getId())
					.orElse(null);

			contrato.setPropiedad(propiedadCompleta);
		}

		EstadoContrato estadoAnterior = null;
		Contrato contratoFinal = contrato;

		if (contrato.getId() != null) {

			Contrato contratoBD = contratoRepository.findById(contrato.getId())
					.orElseThrow(() -> new RuntimeException(
							"Contrato no encontrado con ID: " + contrato.getId()));

			estadoAnterior = contratoBD.getEstado();
			EstadoContrato nuevoEstado = contrato.getEstado();

			if ((estadoAnterior == EstadoContrato.FINALIZADO
					|| estadoAnterior == EstadoContrato.RESCINDIDO)
					&& nuevoEstado == EstadoContrato.ACTIVO) {

				throw new IllegalArgumentException(
						"¡Error! No se puede volver a ACTIVAR un contrato que ya está FINALIZADO o RESCINDIDO.");
			}

			if ((estadoAnterior == EstadoContrato.FINALIZADO
					|| estadoAnterior == EstadoContrato.RESCINDIDO)
					&& nuevoEstado == EstadoContrato.BORRADOR) {

				throw new IllegalArgumentException(
						"¡Error! No se puede volver a BORRADOR un contrato que ya está FINALIZADO o RESCINDIDO.");
			}

			if (estadoAnterior == EstadoContrato.BORRADOR
					&& nuevoEstado != EstadoContrato.ACTIVO
					&& nuevoEstado != EstadoContrato.BORRADOR) {

				throw new IllegalArgumentException(
						"Un contrato en BORRADOR solo puede pasar a estado ACTIVO.");
			}

			if (estadoAnterior == EstadoContrato.ACTIVO
					&& nuevoEstado != EstadoContrato.FINALIZADO
					&& nuevoEstado != EstadoContrato.RESCINDIDO
					&& nuevoEstado != EstadoContrato.ACTIVO) {

				throw new IllegalArgumentException(
						"Un contrato ACTIVO solo puede pasar a FINALIZADO o RESCINDIDO.");
			}

			if (EstadoContrato.BORRADOR.equals(estadoAnterior)) {
				contratoBD.setPropiedad(contrato.getPropiedad());
				contratoBD.setInquilino(contrato.getInquilino());
				contratoBD.setFechaInicio(contrato.getFechaInicio());
				contratoBD.setDuracionMeses(contrato.getDuracionMeses());
				contratoBD.setImporteMensual(contrato.getImporteMensual());
				contratoBD.setDiaVencimientoMensual(contrato.getDiaVencimientoMensual());
				contratoBD.setDescripcion(contrato.getDescripcion());
			}

			if (nuevoEstado == EstadoContrato.ACTIVO
					&& estadoAnterior != EstadoContrato.ACTIVO) {

				List<Contrato> activos = contratoRepository.findByPropiedadIdAndEstado(
						contratoBD.getPropiedad().getId(),
						EstadoContrato.ACTIVO);

				if (!activos.isEmpty()) {
					throw new IllegalArgumentException(
							"¡Error! La propiedad ya cuenta con un contrato ACTIVO vigente.");
				}

				if (contratoBD.getPropiedad() != null) {
					contratoBD.getPropiedad().setEstadoDisponibilidad(EstadoDisponibilidad.ALQUILADA);
					propiedadRepository.save(contratoBD.getPropiedad());
				}
			}

			if ((nuevoEstado == EstadoContrato.FINALIZADO
					|| nuevoEstado == EstadoContrato.RESCINDIDO)
					&& estadoAnterior != nuevoEstado
					&& contratoBD.getPropiedad() != null) {

				contratoBD.getPropiedad().setEstadoDisponibilidad(EstadoDisponibilidad.DISPONIBLE);
				propiedadRepository.save(contratoBD.getPropiedad());
			}

			contratoBD.setEstado(nuevoEstado);
			contratoFinal = contratoBD;

		} else {
			contrato.setEstado(EstadoContrato.BORRADOR);
		}

		Contrato contratoGuardado = contratoRepository.save(contratoFinal);

		if (estadoAnterior == null || !estadoAnterior.equals(contratoGuardado.getEstado())) {
			HistorialEstadoContrato historial = new HistorialEstadoContrato(
					contratoGuardado,
					contratoGuardado.getEstado());

			historialEstadoRepository.save(historial);
		}
	}

	@Override
	public Contrato buscarPorId(Long id) {
		return contratoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException(
						"Contrato no encontrado con el ID: " + id));
	}

	@Override
	public void eliminar(Long id) {

		Contrato contrato = contratoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
						"No se encontró el contrato con ID: " + id));

		if (contrato.getEstado() == EstadoContrato.BORRADOR) {
			contratoRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException(
					"No se puede eliminar: El contrato se encuentra en estado: " + contrato.getEstado());
		}
	}
}