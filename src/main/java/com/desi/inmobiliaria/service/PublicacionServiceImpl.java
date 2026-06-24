package com.desi.inmobiliaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.EstadoPublicacion;
import com.desi.inmobiliaria.entity.HistorialEstadoPublicacion;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.Publicacion;
import com.desi.inmobiliaria.repository.PublicacionesRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionesRepository publicacionRepository;

    @Override
    @Transactional
    public Publicacion crearPublicacion(Publicacion publicacion) {
        
        Propiedad propiedadCompleta = publicacionRepository.buscarPropiedadPorId(publicacion.getPropiedad().getId());
        
        if (propiedadCompleta == null) {
            throw new IllegalArgumentException("Debe seleccionar una propiedad registrada en el sistema.");
        }

        publicacion.setPropiedad(propiedadCompleta);

        if (propiedadCompleta.isEliminada()) {
            throw new IllegalStateException("No se puede publicar una propiedad que ha sido eliminada.");
        }

        if (propiedadCompleta.getEstadoDisponibilidad() != EstadoDisponibilidad.DISPONIBLE) {
            throw new IllegalStateException("Solo se puede crear una publicación para una propiedad que se encuentre disponible.");
        }

        boolean yaTienePublicacionActiva = publicacionRepository.existsByPropiedadIdAndEstado(propiedadCompleta.getId(), EstadoPublicacion.ACTIVA);
        if (yaTienePublicacionActiva) {
            throw new IllegalStateException("No podrá existir más de una publicación activa para la misma propiedad en el mismo período.");
        }

        if (publicacion.getPrecioMensual() == null || publicacion.getPrecioMensual().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio mensual de alquiler deberá ser un número positivo.");
        }

        if (publicacion.getFechaPublicacion() == null) {
            throw new IllegalArgumentException("La fecha de publicación debe ser una fecha válida.");
        }
        
        if (publicacion.getCondiciones() == null || publicacion.getCondiciones().trim().isEmpty() ||
            publicacion.getDescripcion() == null || publicacion.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los datos de la publicación son requeridos.");
        }

        publicacion.setEstado(EstadoPublicacion.ACTIVA);
        publicacion.setEliminada(false);

        HistorialEstadoPublicacion historialInicial = new HistorialEstadoPublicacion();
        historialInicial.setEstado(EstadoPublicacion.ACTIVA);
        historialInicial.setFechaHora(LocalDateTime.now());
        historialInicial.setPublicacion(publicacion); 
        
        publicacion.getHistorialEstados().add(historialInicial);

        return publicacionRepository.save(publicacion);
    }

    @Override
    @Transactional
    public void eliminarPublicacion(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Publicación no encontrada."));
        
        if (publicacion.getEstado() != EstadoPublicacion.ACTIVA) {
            throw new IllegalStateException("Solo se pueden eliminar publicaciones que estén en estado ACTIVA.");
        }
        
        publicacion.setEliminada(true);
        publicacion.setEstado(EstadoPublicacion.ELIMINADA);
        
        HistorialEstadoPublicacion historialBaja = new HistorialEstadoPublicacion();
        historialBaja.setEstado(EstadoPublicacion.ELIMINADA);
        historialBaja.setFechaHora(LocalDateTime.now());
        historialBaja.setPublicacion(publicacion);
        
        publicacion.getHistorialEstados().add(historialBaja);
        
        publicacionRepository.save(publicacion);
    }

    @Override
    @Transactional
    public void modificarPublicacion(Long id, Publicacion datosNuevos) {
        Publicacion existente = publicacionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Publicación no encontrada."));

        if (datosNuevos.getPrecioMensual() == null || datosNuevos.getPrecioMensual().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio mensual debe ser un número positivo.");
        }
        if (datosNuevos.getFechaPublicacion() == null) {
            throw new IllegalArgumentException("La fecha de publicación es obligatoria.");
        }

        // condicon solo si no esta finalizada
        if ("FINALIZADA".equals(existente.getEstado().name()) && 
            !existente.getCondiciones().equals(datosNuevos.getCondiciones())) {
            throw new IllegalStateException("No se pueden modificar las condiciones de alquiler de una publicación FINALIZADA.");
        }

        // reglas a cambiar a activa
        if ("ACTIVA".equals(datosNuevos.getEstado().name()) && !"ACTIVA".equals(existente.getEstado().name())) {
            
            if (existente.getPropiedad().getEstadoDisponibilidad() != EstadoDisponibilidad.DISPONIBLE) {
                throw new IllegalStateException("Solo se puede activar si la propiedad está DISPONIBLE.");
            }
            
            boolean otraActiva = publicacionRepository.existsByPropiedadIdAndEstadoAndIdNot(
                existente.getPropiedad().getId(), EstadoPublicacion.ACTIVA, existente.getId());
            if (otraActiva) {
                throw new IllegalStateException("Ya existe otra publicación ACTIVA para esta propiedad.");
            }
        }

        existente.setPrecioMensual(datosNuevos.getPrecioMensual());
        existente.setFechaPublicacion(datosNuevos.getFechaPublicacion());
        existente.setDescripcion(datosNuevos.getDescripcion());
        existente.setCondiciones(datosNuevos.getCondiciones());

        if (datosNuevos.getEstado() != existente.getEstado()) {
            existente.setEstado(datosNuevos.getEstado());
            
            HistorialEstadoPublicacion nuevoHistorial = new HistorialEstadoPublicacion();
            nuevoHistorial.setEstado(datosNuevos.getEstado());
            nuevoHistorial.setFechaHora(LocalDateTime.now());
            nuevoHistorial.setPublicacion(existente);
            
            existente.getHistorialEstados().add(nuevoHistorial);
        }

        publicacionRepository.save(existente);
    }
}