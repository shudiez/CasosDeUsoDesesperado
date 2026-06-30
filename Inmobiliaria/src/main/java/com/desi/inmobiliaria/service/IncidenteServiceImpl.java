package com.desi.inmobiliaria.service;

import com.desi.inmobiliaria.entity.EstadoIncidente;
import com.desi.inmobiliaria.entity.HistorialEstadoIncidente;
import com.desi.inmobiliaria.entity.Incidente;
import com.desi.inmobiliaria.repository.HistorialEstadoIncidenteRepository;
import com.desi.inmobiliaria.repository.IncidenteRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Indica que esta clase contiene la lógica de negocio
public class IncidenteServiceImpl implements IncidenteService {

    // Repository para trabajar con los incidentes
    @Autowired
    private IncidenteRepository incidenteRepository;

    // Repository para guardar el historial de estados
    @Autowired
    private HistorialEstadoIncidenteRepository historialRepository;

    public IncidenteServiceImpl() {
    }

    // Devuelve todos los incidentes que no fueron eliminados
    @Override
    public List<Incidente> listarTodos() {
        return incidenteRepository.findByEliminadoFalse();
    }

    // Busca un incidente por su id
    @Override
    public Incidente buscarPorId(Long id) {
        return incidenteRepository.findById(id).orElse(null);
    }

    // Guarda un incidente nuevo o editado
    @Override
    public Incidente guardar(Incidente incidente) {

        // Verifico que el título no esté vacío
        if (incidente.getTitulo() == null || incidente.getTitulo().isBlank()) {
            throw new RuntimeException("El título es obligatorio");
        }

        // Verifico que la descripción no esté vacía
        if (incidente.getDescripcion() == null || incidente.getDescripcion().isBlank()) {
            throw new RuntimeException("La descripción es obligatoria");
        }

        // Verifico que tenga una categoría
        if (incidente.getCategoria() == null) {
            throw new RuntimeException("Debe seleccionar una categoría");
        }

        // Verifico que tenga una prioridad
        if (incidente.getPrioridad() == null) {
            throw new RuntimeException("Debe seleccionar una prioridad");
        }

        // Verifico que tenga una propiedad asociada
        if (incidente.getPropiedad() == null) {
            throw new RuntimeException("Debe seleccionar una propiedad");
        }

        // Si no tiene estado, le asigno ABIERTO
        if (incidente.getEstado() == null) {
            incidente.setEstado(EstadoIncidente.ABIERTO);
        }

        // Si es un incidente nuevo, guardo la fecha actual
        if (incidente.getFechaAlta() == null) {
            incidente.setFechaAlta(LocalDateTime.now());
        }

        // Guardo el incidente
        Incidente incidenteGuardado = incidenteRepository.save(incidente);

        // Creo un registro en el historial con el estado actual
        HistorialEstadoIncidente historial = new HistorialEstadoIncidente();
        historial.setIncidente(incidenteGuardado);
        historial.setEstado(incidenteGuardado.getEstado());
        historial.setFechaHora(LocalDateTime.now());

        // Guardo el historial
        historialRepository.save(historial);

        return incidenteGuardado;
    }

    // Elimina un incidente de forma lógica
    @Override
    public void eliminar(Long id) {

        // Busco el incidente
        Incidente incidente = buscarPorId(id);

        // Lo marco como eliminado
        incidente.setEliminado(true);

        // Guardo el cambio
        incidenteRepository.save(incidente);
    }
}
