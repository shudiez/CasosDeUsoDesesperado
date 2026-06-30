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

@Service
public class PropiedadServiceImpl implements PropiedadService {
   @Autowired
   private PropiedadRepository propiedadRepository;
   @Autowired
   private HistorialEstadoPropiedadRepository historialEstadoPropiedadRepository;
   @Autowired
   private ContratoRepository contratoRepository;

   public PropiedadServiceImpl() {
   }

   public Propiedad guardar(Propiedad propiedad) throws Excepcion {
      if (propiedad.getDireccion() != null && !propiedad.getDireccion().isBlank()) {
         if (propiedad.getDescripcion() != null && !propiedad.getDescripcion().isBlank()) {
            if (propiedad.getComodidades() != null && !propiedad.getComodidades().isBlank()) {
               if (propiedad.getCantidadAmbientes() != null && propiedad.getCantidadAmbientes() > 0) {
                  if (propiedad.getMetrosCuadrados() != null && !(propiedad.getMetrosCuadrados() <= (double)0.0F)) {
                     if (propiedad.getEstadoDisponibilidad() == null) {
                        propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.DISPONIBLE);
                     }

                     Propiedad propiedadExistente = this.propiedadRepository.findByDireccionAndCiudad(propiedad.getDireccion(), propiedad.getCiudad());
                     if (propiedadExistente != null && !propiedadExistente.getId().equals(propiedad.getId())) {
                        throw new Excepcion("Ya existe una propiedad con esa dirección y ciudad");
                     } else if (propiedad.getId() != null && (propiedad.getEstadoDisponibilidad() == EstadoDisponibilidad.DISPONIBLE || propiedad.getEstadoDisponibilidad() == EstadoDisponibilidad.INACTIVA) && this.contratoRepository.existsByPropiedadAndEstadoAndEliminadoFalse(propiedad, EstadoContrato.ACTIVO)) {
                        throw new Excepcion("No se puede cambiar el estado porque existe un contrato activo");
                     } else if (propiedad.getCiudad() == null) {
                        throw new Excepcion("La ciudad es obligatoria");
                     } else if (propiedad.getPropietario() == null) {
                        throw new Excepcion("El propietario es obligatorio");
                     } else if (propiedad.getTipo() == null) {
                        throw new Excepcion("Debe seleccionar un tipo de propiedad");
                     } else {
                        Propiedad propiedadGuardada = (Propiedad)this.propiedadRepository.save(propiedad);
                        HistorialEstadoPropiedad historial = new HistorialEstadoPropiedad();
                        historial.setPropiedad(propiedadGuardada);
                        historial.setEstado(propiedadGuardada.getEstadoDisponibilidad());
                        historial.setFechaHora(LocalDateTime.now());
                        this.historialEstadoPropiedadRepository.save(historial);
                        return propiedadGuardada;
                     }
                  } else {
                     throw new Excepcion("Los metros cuadrados deben ser mayores a cero");
                  }
               } else {
                  throw new Excepcion("La cantidad de ambientes debe ser mayor a cero");
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

   public List<Propiedad> listarTodas() {
      return this.propiedadRepository.findByEliminadaFalse();
   }

   public Propiedad buscarPorId(Long id) {
      return (Propiedad)this.propiedadRepository.findById(id).orElseThrow(() -> new EntidadNoEncontradaException("la propiedad", id));
   }

   public void eliminar(Long id) throws Excepcion {
      Propiedad propiedad = this.buscarPorId(id);
      if (this.contratoRepository.existsByPropiedadAndEstadoAndEliminadoFalse(propiedad, EstadoContrato.ACTIVO)) {
         throw new Excepcion("No se puede eliminar la propiedad porque tiene un contrato activo vigente");
      } else {
         propiedad.setEliminada(true);
         this.propiedadRepository.save(propiedad);
      }
   }

   public List<Propiedad> buscarPorDireccion(String direccion) {
      return this.propiedadRepository.findByDireccionContainingIgnoreCaseAndEliminadaFalse(direccion);
   }

   public List<Propiedad> buscarPorCiudad(Long ciudadId) {
      return this.propiedadRepository.findByCiudadIdAndEliminadaFalse(ciudadId);
   }

   public List<Propiedad> buscarPorTipo(TipoPropiedad tipo) {
      return this.propiedadRepository.findByTipoAndEliminadaFalse(tipo);
   }

   public List<Propiedad> buscarPorEstado(EstadoDisponibilidad estado) {
      return this.propiedadRepository.findByEstadoDisponibilidadAndEliminadaFalse(estado);
   }

   public List<Propiedad> buscarConFiltros(String direccion, Long ciudadId, TipoPropiedad tipo, EstadoDisponibilidad estado) {
      return this.propiedadRepository.buscarConFiltros(direccion, ciudadId, tipo, estado);
   }
}
