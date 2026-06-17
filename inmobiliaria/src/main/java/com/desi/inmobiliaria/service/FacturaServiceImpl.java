package com.desi.inmobiliaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.desi.inmobiliaria.entity.Factura;
import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.entity.EstadoFactura;
import com.desi.inmobiliaria.excepciones.Excepcion;
import com.desi.inmobiliaria.excepciones.EntidadNoEncontradaException;
import com.desi.inmobiliaria.repository.ContratoRepository;
import com.desi.inmobiliaria.repository.FacturaRepository;
import com.desi.inmobiliaria.repository.HistorialEstadoFacturaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.desi.inmobiliaria.entity.HistorialEstadoFactura;

import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService {

	@Autowired
	private FacturaRepository facturaRepository;

	@Autowired
	private HistorialEstadoFacturaRepository historialRepository;

	@Autowired
	private ContratoRepository contratoRepository;

	@Override
	public List<Factura> buscarConFiltros(Long contratoId, Long inquilinoId, Long propiedadId, EstadoFactura estado,
			LocalDate desde, LocalDate hasta) {
		return facturaRepository.buscarConFiltros(contratoId, inquilinoId, propiedadId, estado, desde, hasta);
	}

	@Override
	public List<Factura> getAll() {
		return facturaRepository.buscarConFiltros(null, null, null, null, null, null);
	}

	// 4.2 buscar para precargar formulario
	@Override
	public Factura getById(Long id) throws Excepcion {
		return facturaRepository.findById(id).filter(f -> !f.isEliminado())
				.orElseThrow(() -> new EntidadNoEncontradaException("la factura", id));
	}

	// 4.1 alta
	@Override
	public void save(Factura factura) throws Excepcion {
		if (factura.getContrato() == null || factura.getContrato().getId() == null) {
			throw new Excepcion("Debe seleccionar un contrato válido.");
		}
		Contrato contratoReal = contratoRepository.findById(factura.getContrato().getId())
				.orElseThrow(() -> new Excepcion("El contrato seleccionado no existe en el sistema."));

		if (contratoReal.getEstado() == EstadoContrato.FINALIZADO
				|| contratoReal.getEstado() == EstadoContrato.RESCINDIDO
				|| contratoReal.getEstado() == EstadoContrato.BORRADOR) {

			throw new Excepcion(
					"No se puede crear una factura para un contrato en estado: " + contratoReal.getEstado());
		}

		if (contratoReal.isEliminado()) {
			throw new Excepcion("No se puede crear una factura para un contrato que ha sido eliminado.");
		}

		if (factura.getFechaVencimiento() != null && factura.getFechaEmision() != null) {
			if (factura.getFechaVencimiento().isBefore(factura.getFechaEmision())) {
				throw new Excepcion("La fecha de vencimiento no puede ser anterior a la fecha de emisión.");
			}
		}

		if (factura.getInteres() != null && factura.getImporte() != null) {
			factura.setImporte(factura.getImporte().add(factura.getInteres()));
		}

		factura.setEstado(EstadoFactura.PENDIENTE);
		factura.setEliminado(false);
		facturaRepository.save(factura);
	}

	// 4.2 modificación
	@Override
	public void update(Factura factura) throws Excepcion {
		Factura existente = facturaRepository.findById(factura.getId()).orElseThrow(
				() -> new RuntimeException("No se encontró la factura a modificar con ID: " + factura.getId()));

		if (existente.getEstado() == EstadoFactura.ANULADA || existente.getEstado() == EstadoFactura.PAGADA) {
			throw new Excepcion("No se puede modificar una factura que ya se encuentra PAGADA o ANULADA.");
		}

		if (factura.getFechaVencimiento() != null && factura.getFechaEmision() != null) {
			if (factura.getFechaVencimiento().isBefore(factura.getFechaEmision())) {
				throw new Excepcion("La fecha de vencimiento no puede ser anterior a la fecha de emisión.");
			}
		}

		EstadoFactura estadoAnterior = existente.getEstado();
		EstadoFactura estadoNuevo = factura.getEstado();

		if (estadoAnterior != estadoNuevo) {
			if (estadoAnterior == EstadoFactura.VENCIDA && estadoNuevo != EstadoFactura.PAGADA) {
				throw new Excepcion("Una factura VENCIDA solo puede pasar al estado PAGADA.");
			}
			if (estadoNuevo == EstadoFactura.ANULADA && estadoAnterior != EstadoFactura.PENDIENTE) {
				throw new Excepcion("Solo se pueden anular facturas que estén en estado PENDIENTE.");
			}
		}

		if (estadoNuevo == EstadoFactura.PAGADA) {
			if (factura.getFechaPago() == null || factura.getMedio() == null || factura.getImportePagado() == null) {
				throw new Excepcion(
						"Si la factura pasa a estado PAGADA, debe completar la fecha, el medio y el importe pagado.");
			}

			existente.setFechaPago(factura.getFechaPago());
			existente.setMedio(factura.getMedio());
			existente.setImportePagado(factura.getImportePagado());
		} else {
			if (factura.getFechaPago() != null || factura.getMedio() != null || factura.getImportePagado() != null) {
				throw new Excepcion("No se pueden registrar datos de pago si la factura no está en estado PAGADA.");
			}
			existente.setFechaPago(null);
			existente.setMedio(null);
			existente.setImportePagado(null);
		}

		existente.setConceptoFacturado(factura.getConceptoFacturado());
		existente.setFechaEmision(factura.getFechaEmision());
		existente.setFechaVencimiento(factura.getFechaVencimiento());
		existente.setImporte(factura.getImporte());
		existente.setEstado(factura.getEstado());
		existente.setFechaPago(factura.getFechaPago());
		existente.setMedio(factura.getMedio());
		existente.setImportePagado(factura.getImportePagado());

		existente.setInteres(factura.getInteres());

		if (factura.getInteres() != null && factura.getImporte() != null) {
			existente.setImporte(factura.getImporte().add(factura.getInteres()));
		} else {
			existente.setImporte(factura.getImporte());
		}

		facturaRepository.save(existente);

		if (estadoAnterior != estadoNuevo) {
			historialRepository.save(new com.desi.inmobiliaria.entity.HistorialEstadoFactura(existente, estadoNuevo));
		}
	}

	// 4.3 eliminación
	@Override
	public void deleteById(Long id) {
		Factura factura = facturaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontró la factura a eliminar"));

		if (factura.getEstado() == com.desi.inmobiliaria.entity.EstadoFactura.PAGADA) {
			throw new RuntimeException("No se puede eliminar una factura que ya ha sido PAGADA.");
		}

		factura.setEstado(EstadoFactura.ANULADA);
		factura.setEliminado(true);

		facturaRepository.save(factura);
	}
}