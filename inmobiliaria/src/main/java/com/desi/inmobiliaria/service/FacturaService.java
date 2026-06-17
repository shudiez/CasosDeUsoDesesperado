package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.EstadoFactura;

import java.time.LocalDate;
import java.util.List;
import com.desi.inmobiliaria.entity.Factura;
import com.desi.inmobiliaria.excepciones.Excepcion;

public interface FacturaService {
	// 4.4 listado
	List<Factura> getAll();

	// 4.2 modificación
	Factura getById(Long id) throws Excepcion;

	// 4.1 alta
	void save(Factura factura) throws Excepcion;

	// 4.2 modificación
	void update(Factura factura) throws Excepcion;

	// 4.3 eliminación
	void deleteById(Long id) throws Excepcion;

	List<Factura> buscarConFiltros(Long contratoId, Long inquilinoId, Long propiedadId, EstadoFactura estado,
			LocalDate desde, LocalDate hasta);
}
