package com.desi.inmobiliaria.service;

import java.time.LocalDate;
import java.util.List;

import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoContrato;

public interface ContratoService {

	// EL MÉTODO QUE FALTABA: Permite que tu controlador liste los contratos en el
	// combo
	List<Contrato> listarTodos();

	void guardar(Contrato contrato);

	Contrato buscarPorId(Long id);

	void eliminar(Long id);

	// Métodos que dejó definidos tu compañero para sus filtros
	void cambiarEstado(Long id, EstadoContrato nuevoEstado);

	List<Contrato> listarConFiltros(String param1, String param2, EstadoContrato estado, LocalDate fecha);
}