package com.desi.inmobiliaria.service;

import java.util.List;

import com.desi.inmobiliaria.entity.Provincia;

public interface ProvinciaService {

	// METODO LISTAR PROVINCIAS
	List<Provincia> listarTodas();

	// METODO BUSCAR PROVINCIA
	Provincia buscarPorId(Long id);

	// METODO GUARDAR PROVINCIA
	Provincia guardar(Provincia provincia);

	// METODO ELIMINAR PROVINCIA
	void eliminar(Long id);

}