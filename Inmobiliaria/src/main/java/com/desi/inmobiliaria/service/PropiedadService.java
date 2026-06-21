package com.desi.inmobiliaria.service;

import java.util.List;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;

import excepciones.Excepcion;

public interface PropiedadService {

	// METODO GUARDAR PROPIEDAD
	Propiedad guardar(Propiedad propiedad) throws Excepcion;

	// METODO LISTAR PROPIEDAD
	List<Propiedad> listarTodas();

	// METODO BUSCAR PROPIEDAD
	Propiedad buscarPorId(Long id);

	// METODO ELIMINAR PROPIEDAD
	void eliminar(Long id) throws Excepcion;

	// METODO BUSCAR PROPIEDAD POR DIRECCION
	List<Propiedad> buscarPorDireccion(String direccion);

	// METODO BUSCAR PROPIEDAD POR CIUDAD
	List<Propiedad> buscarPorCiudad(Long ciudadId);

	// METODO BUSCAR PROPIEDAD POR TIPO
	List<Propiedad> buscarPorTipo(TipoPropiedad tipo);

	// METODO BUSCAR PROPIEDAD POR ESTADO
	List<Propiedad> buscarPorEstado(EstadoDisponibilidad estado);
}
