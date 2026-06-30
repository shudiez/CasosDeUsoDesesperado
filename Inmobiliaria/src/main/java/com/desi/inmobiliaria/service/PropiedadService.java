package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;
import excepciones.Excepcion;
import java.util.List;

public interface PropiedadService {

   // Guarda una propiedad nueva o editada
   Propiedad guardar(Propiedad propiedad) throws Excepcion;

   // Devuelve todas las propiedades
   List<Propiedad> listarTodas();

   // Busca una propiedad por su id
   Propiedad buscarPorId(Long id);

   // Elimina una propiedad
   void eliminar(Long id) throws Excepcion;

   // Busca propiedades por dirección
   List<Propiedad> buscarPorDireccion(String direccion);

   // Busca propiedades de una ciudad
   List<Propiedad> buscarPorCiudad(Long ciudadId);

   // Busca propiedades según su tipo
   List<Propiedad> buscarPorTipo(TipoPropiedad tipo);

   // Busca propiedades según su estado
   List<Propiedad> buscarPorEstado(EstadoDisponibilidad estado);

   // Busca propiedades combinando varios filtros
   List<Propiedad> buscarConFiltros(String direccion,
                                    Long ciudadId,
                                    TipoPropiedad tipo,
                                    EstadoDisponibilidad estado);
}
