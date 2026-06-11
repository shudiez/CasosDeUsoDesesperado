package com.desi.inmobiliria.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.desi.entity.Contrato;
import com.desi.entity.EstadoContrato;
import com.desi.entity.EstadoDisponibilidad;
import com.desi.entity.HistorialEstadoContrato;
import com.desi.entity.Propiedad;
import com.desi.repository.ContratoRepository;
import com.desi.repository.PropiedadRepository;
import com.desi.repository.HistorialEstadoRepository;

@Service
public class ContratoServiceImpl implements ContratoService{

	// Dependencia del repositorio (Base de datos)
    private final ContratoRepository contratoRepository;
    private final PropiedadRepository propiedadRepository; /*  AGREGADO	*/
    private final HistorialEstadoRepository historialEstadoRepository;

    // Inyección por constructor
    public ContratoServiceImpl(ContratoRepository contratoRepository, PropiedadRepository propiedadRepository, HistorialEstadoRepository historialEstadoRepository) {
        this.contratoRepository = contratoRepository;
        this.propiedadRepository = propiedadRepository;
        this.historialEstadoRepository= historialEstadoRepository;
    }

    @Override
    public List<Contrato> listarConFiltros(String propiedad, String inquilino, EstadoContrato estado, LocalDate fechaInicio) {
        // Si los textos vienen vacíos desde el HTML, los pasamos a null
        String propFiltro = (propiedad != null && !propiedad.isBlank()) ? propiedad : null;
        String inqFiltro = (inquilino != null && !inquilino.isBlank()) ? inquilino : null;

        return contratoRepository.filtrarContratos(propFiltro, inqFiltro, estado, fechaInicio);
    }
    
    
    @Override
    public void guardar(Contrato contrato) {

        // 🌟 PASO OBLIGATORIO: Ir a buscar la propiedad completa a la BD usando su ID.
        // Esto reengancha las propiedades nuevas que elegís en el HTML y evita que se pierdan.
        if (contrato.getPropiedad() != null && contrato.getPropiedad().getId() != null) {
            Propiedad propiedadCompleta = propiedadRepository.findById(contrato.getPropiedad().getId()).orElse(null);
            contrato.setPropiedad(propiedadCompleta);
        }
    	
        ////
        // 🌟 HISTORIAL - PARTE A: Averiguamos el estado anterior antes de que impacte el .save()
        EstadoContrato estadoAnterior = null;
        if (contrato.getId() != null) {
            // Si ya tiene ID, es una edición. Vamos a buscar cómo estaba guardado en la BD.
            Contrato contratoViejo = contratoRepository.findById(contrato.getId()).orElse(null);
            if (contratoViejo != null) {
                estadoAnterior = contratoViejo.getEstado();
            }
        }
        
        ////
        
        
        // 1. REGLA DE NEGOCIO: Solo validamos si se intenta pasar a ACTIVO
        if (contrato.getEstado() == EstadoContrato.ACTIVO) {
            
            // Buscamos si ya existe un contrato activo en la BD para esa propiedad
            Contrato contratoActivoEnBD = contratoRepository.findByPropiedadAndEstadoAndEliminadoFalse(
                contrato.getPropiedad(), 
                EstadoContrato.ACTIVO
            );
            
            if (contratoActivoEnBD != null) {
                // Caso Nuevo: Si es un alta, rebota de una
                if (contrato.getId() == null) {
                    throw new RuntimeException("La propiedad seleccionada ya posee un contrato ACTIVO vigente.");
                }
                // Caso Edición: Si el ID activo de la BD es distinto al nuestro, rebota
                if (!contratoActivoEnBD.getId().equals(contrato.getId())) {
                    throw new RuntimeException("La propiedad seleccionada ya posee un contrato ACTIVO vigente.");
                }
            }
            
            // 2. ¡Efecto en cadena! Si pasa las validaciones, cambiamos el estado de la propiedad
            Propiedad propiedad = contrato.getPropiedad();
            if (propiedad != null) {
                propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.ALQUILADA);
                // Aseguramos el guardado de la propiedad por las dudas
                propiedadRepository.save(propiedad); 
            }
        }

        // 🌟 LA LÍNEA MÁGICA QUE TE FALTABA: Guardar el contrato en la base de datos 🌟
        // Esto tiene que estar AFUERA del "if (EstadoContrato.ACTIVO)" para que también guarde los Borradores.
        //contratoRepository.save(contrato);
        
        ////
        Contrato contratoGuardado = contratoRepository.save(contrato);
     // 🌟 HISTORIAL - PARTE B: Si el contrato es nuevo (estadoAnterior == null) o el estado cambió
        if (estadoAnterior == null || !estadoAnterior.equals(contratoGuardado.getEstado())) {
            
            // Usamos tu constructor exacto (Contrato, EstadoContrato)
            HistorialEstadoContrato historial = new HistorialEstadoContrato(
                contratoGuardado, 
                contratoGuardado.getEstado()
            );
            
            // Persistimos el renglón del historial en la base de datos
            historialEstadoRepository.save(historial);
        }
        ////
    }
    
    
    
    
    /*
    @Override
    public void guardar(Contrato contrato) {

    	if (contrato.getEstado() == EstadoContrato.ACTIVO) {
    	    
    	    // 1. Ejecutás la validación del contrato activo único (el existsBy... que hicimos antes)
    	    boolean yaExisteActivo = contratoRepository.existsByPropiedadAndEstadoAndEliminadoFalse(
    	        contrato.getPropiedad(), 
    	        EstadoContrato.ACTIVO
    	    );
    	    
    	    if (yaExisteActivo) {
    	        // Validación para evitar pisar el mismo contrato en caso de edición
    	        if (contrato.getId() == null || !contratoRepository.findByPropiedadAndEstadoAndEliminadoFalse(contrato.getPropiedad(), EstadoContrato.ACTIVO).getId().equals(contrato.getId())) {
    	            throw new RuntimeException("La propiedad seleccionada ya posee un contrato ACTIVO vigente.");
    	        }
    	    }
    	 // 2. ¡Efecto en cadena! Cambiás el estado de la propiedad usando tu enum real
    	    Propiedad propiedad = contrato.getPropiedad();
    	    if (propiedad != null) {
    	        propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.ALQUILADA);
    	        
    	        // 💡 Tip de Persistencia: Si en tu entidad Contrato tenés la relación con la propiedad 
    	        // configurada con CascadeType.MERGE o CascadeType.ALL, al guardar el contrato se guardará la propiedad automáticamente.
    	        // Si no es el caso, lo ideal es que inyectes PropiedadRepository en este servicio y hagas:
    	        // propiedadRepository.save(propiedad);
    	    }
    	}
    	
    }
*/
    
    
    @Override
    public Contrato buscarPorId(Long id) {
        // .findById() devuelve un Optional. Si no lo encuentra, lanzamos una excepción 
        // o puedes retornar null: .orElse(null);
        return contratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado con el ID: " + id));
    }

    @Override
    public void eliminar(Long id) {
        // .deleteById() elimina el registro de la base de datos
        contratoRepository.deleteById(id);
    }
	
}
