package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.HistorialEstadoPropiedad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;
import com.desi.inmobiliaria.repository.ContratoRepository;
import com.desi.inmobiliaria.repository.HistorialEstadoPropiedadRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;
import excepciones.EntidadNoEncontradaException;
import excepciones.Excepcion;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase contiene la lógica de negocio
public class PropiedadServiceImpl implements PropiedadService {

   // Repository para trabajar con las propiedades
   @Autowired
   private PropiedadRepository propiedadRepository;

   // Repository para guardar el historial de estados
   @Autowired
   private HistorialEstadoPropiedadRepository historialEstadoPropiedadRepository;

   // Lo uso para verificar si una propiedad tiene contratos activos
   @Autowired
   private ContratoRepository contratoRepository;

   public PropiedadServiceImpl() {
   }

   // Guarda una propiedad nueva o editada
   @Override
   public Propiedad guardar(Propiedad propiedad) throws Excepcion {

      // Verifico que la dirección no esté vacía
      if (propiedad.getDireccion() != null && !propiedad.getDireccion().isBlank()) {

         // Verifico que la descripción no esté vacía
         if (propiedad.getDescripcion() != null && !propiedad.getDescripcion().isBlank()) {

            // Verifico que las comodidades no estén vacías
            if (propiedad.getComodidades() != null && !propiedad.getComodidades().isBlank()) {

               // Verifico que la cantidad de ambientes sea mayor a cero
               if (propiedad.getCantidadAmbientes() != null
                       && propiedad.getCantidadAmbientes() > 0) {

                  // Verifico que los metros cuadrados sean mayores a cero
                  if (propiedad.getMetrosCuadrados() != null
                          && propiedad.getMetrosCuadrados() > 0) {

                     // Si no tiene estado, le asigno DISPONIBLE
                     if (propiedad.getEstadoDisponibilidad() == null) {
                        propiedad.setEstadoDisponibilidad(
                                EstadoDisponibilidad.DISPONIBLE);
                     }

                     // Verifico que no exista otra propiedad con la misma dirección
                     // en la misma ciudad
                     Propiedad propiedadExistente =
                             propiedadRepository.findByDireccionAndCiudad(
                                     propiedad.getDireccion(),
                                     propiedad.getCiudad());

                     if (propiedadExistente != null
                             && !propiedadExistente.getId().equals(propiedad.getId())) {

                        throw new Excepcion(
                                "Ya existe una propiedad con esa dirección y ciudad");

                     }

                     // Si la propiedad tiene un contrato activo,
                     // no permito cambiar su estado
                     else if (propiedad.getId() != null
                             && (propiedad.getEstadoDisponibilidad()
                             == EstadoDisponibilidad.DISPONIBLE
                             || propiedad.getEstadoDisponibilidad()
                             == EstadoDisponibilidad.INACTIVA)
                             && contratoRepository
                             .existsByPropiedadAndEstadoAndEliminadoFalse(
                                     propiedad,
                                     EstadoContrato.ACTIVO)) {

                        throw new Excepcion(
                                "No se puede cambiar el estado porque existe un contrato activo");

                     }

                     // Verifico que tenga ciudad
                     else if (propiedad.getCiudad() == null) {

                        throw new Excepcion("La ciudad es obligatoria");

                     }

                     // Verifico que tenga propietario
                     else if (propiedad.getPropietario() == null) {

                        throw new Excepcion("El propietario es obligatorio");

                     }

                     // Verifico que tenga tipo
                     else if (propiedad.getTipo() == null) {

                        throw new Excepcion(
                                "Debe seleccionar un tipo de propiedad");

                     }

                     // Si todas las validaciones están bien, guardo la propiedad
                     else {

                        Propiedad propiedadGuardada =
                                propiedadRepository.save(propiedad);

                        // Guardo el estado inicial en el historial
                        HistorialEstadoPropiedad historial =
                                new HistorialEstadoPropiedad();

                        historial.setPropiedad(propiedadGuardada);
                        historial.setEstado(
                                propiedadGuardada.getEstadoDisponibilidad());

                        // Guardo la fecha y hora del cambio
                        historial.setFechaHora(LocalDateTime.now());

                        historialEstadoPropiedadRepository.save(historial);

                        return propiedadGuardada;
                     }

                  } else {
                     throw new Excepcion(
                             "Los metros cuadrados deben ser mayores a cero");
                  }

               } else {
                  throw new Excepcion(
                          "La cantidad de ambientes debe ser mayor a cero");
               }

            } else {
               throw new Excepcion("Las comodidades son obligatorias");
            }

         } else {
            throw new Excepcion("La descripción es obligatoria");
         }

      } else {
         throw new Excepcion("La dirección es obligatoria");
      }
   }

   // Devuelve todas las propiedades que no fueron eliminadas
   @Override
   public List<Propiedad> listarTodas() {
      return propiedadRepository.findByEliminadaFalse();
   }

   // Busca una propiedad por su id
   @Override
   public Propiedad buscarPorId(Long id) {

      return propiedadRepository.findById(id)
              .orElseThrow(() ->
                      new EntidadNoEncontradaException("la propiedad", id));
   }

   // Elimina una propiedad de forma lógica
   @Override
   public void eliminar(Long id) throws Excepcion {

      Propiedad propiedad = buscarPorId(id);

      // Si tiene un contrato activo no permito eliminarla
      if (contratoRepository.existsByPropiedadAndEstadoAndEliminadoFalse(
              propiedad,
              EstadoContrato.ACTIVO)) {

         throw new Excepcion(
                 "No se puede eliminar la propiedad porque tiene un contrato activo vigente");

      }

      // La marco como eliminada
      propiedad.setEliminada(true);

      propiedadRepository.save(propiedad);
   }

   // Busca propiedades por dirección
   @Override
   public List<Propiedad> buscarPorDireccion(String direccion) {
      return propiedadRepository
              .findByDireccionContainingIgnoreCaseAndEliminadaFalse(direccion);
   }

   // Busca propiedades por ciudad
   @Override
   public List<Propiedad> buscarPorCiudad(Long ciudadId) {
      return propiedadRepository.findByCiudadIdAndEliminadaFalse(ciudadId);
   }

   // Busca propiedades por tipo
   @Override
   public List<Propiedad> buscarPorTipo(TipoPropiedad tipo) {
      return propiedadRepository.findByTipoAndEliminadaFalse(tipo);
   }

   // Busca propiedades por estado
   @Override
   public List<Propiedad> buscarPorEstado(EstadoDisponibilidad estado) {
      return propiedadRepository
              .findByEstadoDisponibilidadAndEliminadaFalse(estado);
   }

   // Busca propiedades combinando varios filtros
   @Override
   public List<Propiedad> buscarConFiltros(
           String direccion,
           Long ciudadId,
           TipoPropiedad tipo,
           EstadoDisponibilidad estado) {

      return propiedadRepository.buscarConFiltros(
              direccion,
              ciudadId,
              tipo,
              estado);
   }
}
