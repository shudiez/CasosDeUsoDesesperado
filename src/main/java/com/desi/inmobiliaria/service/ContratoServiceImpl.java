package com.desi.inmobiliaria.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.desi.inmobiliaria.entity.Contrato;
import com.desi.inmobiliaria.entity.EstadoContrato;
import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.HistorialEstadoContrato;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.repository.ContratoRepository;
import com.desi.inmobiliaria.repository.PropiedadRepository;
import com.desi.inmobiliaria.service.ContratoService;
import com.desi.inmobiliaria.repository.HistorialEstadoContratoRepository;

@Service
public class ContratoServiceImpl implements ContratoService{

	// Dependencia del repositorio (Base de datos)
    private final ContratoRepository contratoRepository;
    private final PropiedadRepository propiedadRepository;
    private final HistorialEstadoContratoRepository historialEstadoRepository;

    // Inyección por constructor
    public ContratoServiceImpl(ContratoRepository contratoRepository, PropiedadRepository propiedadRepository, HistorialEstadoContratoRepository historialEstadoRepository) {
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

        // 🌟 PASO OBLIGATORIO: Reenganchar la propiedad completa
        if (contrato.getPropiedad() != null && contrato.getPropiedad().getId() != null) {
            Propiedad propiedadCompleta = propiedadRepository.findById(contrato.getPropiedad().getId()).orElse(null);
            contrato.setPropiedad(propiedadCompleta);
        }

        // 🌟 HISTORIAL - PARTE A: Averiguamos el estado anterior
        EstadoContrato estadoAnterior = null;
        if (contrato.getId() != null) {
            Contrato contratoViejo = contratoRepository.findById(contrato.getId()).orElse(null);
            if (contratoViejo != null) {
                estadoAnterior = contratoViejo.getEstado();
            }
        } else {
        	
                contrato.setEstado(EstadoContrato.BORRADOR);
           
        }

        // Guardamos el contrato con el estado decidido automáticamente
        Contrato contratoGuardado = contratoRepository.save(contrato);

        // 🌟 HISTORIAL - PARTE B: Registro en el historial si es nuevo o cambió
        if (estadoAnterior == null || !estadoAnterior.equals(contratoGuardado.getEstado())) {
            HistorialEstadoContrato historial = new HistorialEstadoContrato(
                contratoGuardado, 
                contratoGuardado.getEstado()
            );
            historialEstadoRepository.save(historial);
        }
    }
    
      
            
    
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
    
    
    // controlar cambios de estado en los contratos
    
    @Override
    public void cambiarEstado(Long contratoId, EstadoContrato nuevoEstado) {
        // 1. Buscar el contrato actual en la base de datos
        Contrato contrato = contratoRepository.findById(contratoId)
            .orElseThrow(() -> new RuntimeException("Contrato no encontrado con el ID: " + contratoId));

        EstadoContrato estadoActual = contrato.getEstado();

        // 2. Controlar que no intenten revivir un contrato
        if ((estadoActual == EstadoContrato.FINALIZADO || estadoActual == EstadoContrato.RESCINDIDO) 
                && nuevoEstado == EstadoContrato.ACTIVO) {
            throw new IllegalArgumentException("¡Error! No se puede volver a ACTIVAR un contrato que ya está FINALIZADO o RESCINDIDO.");
        }
        
        // ****++++  Controla que un contrato finalizado o rescindido no se pueda poner en borrador, Así evito que se pueda volver a ACTIVAR.
        if ((estadoActual == EstadoContrato.FINALIZADO || estadoActual == EstadoContrato.RESCINDIDO) 
                && nuevoEstado == EstadoContrato.BORRADOR) {
            throw new IllegalArgumentException("¡Error! No se puede volver a BORRADOR un contrato que ya está FINALIZADO o RESCINDIDO.");
        }
        

        // 3. Controlar el paso de Borrador a Activo
        if (estadoActual == EstadoContrato.BORRADOR && nuevoEstado != EstadoContrato.ACTIVO) {
            throw new IllegalArgumentException("Un contrato en BORRADOR solo puede pasar a estado ACTIVO.");
        }
        
        // 4. Controlar las salidas desde Activo
        if (estadoActual == EstadoContrato.ACTIVO 
                && nuevoEstado != EstadoContrato.FINALIZADO 
                && nuevoEstado != EstadoContrato.RESCINDIDO) {
            throw new IllegalArgumentException("Un contrato ACTIVO solo puede pasar a FINALIZADO o RESCINDIDO.");
        }
        
        Propiedad propiedad = contrato.getPropiedad();
        
        if (nuevoEstado == EstadoContrato.ACTIVO) {
        /*if (nuevoEstado == EstadoContrato.ACTIVO && estadoActual != EstadoContrato.ACTIVO) {*/
            // Buscamos si la propiedad ya tiene un contrato ACTIVO en la base de datos
            List<Contrato> activos = contratoRepository.findByPropiedadIdAndEstado(contrato.getPropiedad().getId(), EstadoContrato.ACTIVO);
            
            // Si la lista no está vacía, significa que otra persona ya la tiene alquilada
            if (!activos.isEmpty()) {
                throw new IllegalArgumentException("¡Error! La propiedad ya cuenta con un contrato ACTIVO vigente.");
            }
            
            if(propiedad != null) {
            	propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.ALQUILADA);
            	propiedadRepository.save(propiedad);
            }
             
        }
         //  Lógica para pasar de de FINALIZADO O RESCINDIDO a propiedad DISPONIBLE.
            if (nuevoEstado == EstadoContrato.FINALIZADO || nuevoEstado == EstadoContrato.RESCINDIDO) {
                if (propiedad != null) {
                    // REGLA: La propiedad vuelve a estar DISPONIBLE
                    propiedad.setEstadoDisponibilidad(EstadoDisponibilidad.DISPONIBLE);
                    propiedadRepository.save(propiedad);
                }
            }
            
       

        // 5. Si no saltó ningún error, guardamos el nuevo estado en la BD
        contrato.setEstado(nuevoEstado);
        contratoRepository.save(contrato);
        
    }
}
	

