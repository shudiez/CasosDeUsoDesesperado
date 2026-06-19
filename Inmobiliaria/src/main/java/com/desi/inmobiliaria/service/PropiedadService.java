package com.desi.inmobiliaria.service;

import java.util.List;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;

import excepciones.Excepcion;

public interface PropiedadService {

	Propiedad guardar(Propiedad propiedad) throws Excepcion;

	List<Propiedad> listarTodas();

	Propiedad buscarPorId(Long id);

	void eliminar(Long id) throws Excepcion;

	List<Propiedad> buscarPorDireccion(String direccion);

	List<Propiedad> buscarPorCiudad(Long ciudadId);

	List<Propiedad> buscarPorTipo(TipoPropiedad tipo);

	List<Propiedad> buscarPorEstado(EstadoDisponibilidad estado);
}
