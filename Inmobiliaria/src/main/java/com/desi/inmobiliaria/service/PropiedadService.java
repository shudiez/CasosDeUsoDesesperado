package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;
import excepciones.Excepcion;
import java.util.List;

public interface PropiedadService {
   Propiedad guardar(Propiedad propiedad) throws Excepcion;

   List<Propiedad> listarTodas();

   Propiedad buscarPorId(Long id);

   void eliminar(Long id) throws Excepcion;

   List<Propiedad> buscarPorDireccion(String direccion);

   List<Propiedad> buscarPorCiudad(Long ciudadId);

   List<Propiedad> buscarPorTipo(TipoPropiedad tipo);

   List<Propiedad> buscarPorEstado(EstadoDisponibilidad estado);

   List<Propiedad> buscarConFiltros(String direccion, Long ciudadId, TipoPropiedad tipo, EstadoDisponibilidad estado);
}
