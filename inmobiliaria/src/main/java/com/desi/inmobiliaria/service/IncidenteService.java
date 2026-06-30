package com.desi.inmobiliaria.service;

import java.util.List;

import com.desi.inmobiliaria.entity.Incidente;

public interface IncidenteService {

	List<Incidente> listarTodos();

	Incidente guardar(Incidente incidente);

	Incidente buscarPorId(Long id);

	void eliminar(Long id);

}