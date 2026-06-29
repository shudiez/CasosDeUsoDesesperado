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
    	
    	// Validaciones lógicas manuales como "red de seguridad"
        if (contrato.getDiaVencimientoMensual() < 1 || contrato.getDiaVencimientoMensual() > 31) {
            throw new IllegalArgumentException("¡Error! El día de vencimiento mensual debe estar entre 1 y 31.");
        }
        if (contrato.getDuracionMeses() < 1 || contrato.getDuracionMeses() > 36) {
            throw new IllegalArgumentException("¡Error! La duración del alquiler debe ser mayor a cero y menor de 36.");
        }
        
        
        
        //  PASO OBLIGATORIO: Reenganchar la propiedad completa
        if (contrato.getPropiedad() != null && contrato.getPropiedad().getId() != null) {
            Propiedad propiedadCompleta = propiedadRepository.findById(contrato.getPropiedad().getId()).orElse(null);
            contrato.setPropiedad(propiedadCompleta);
        }

        EstadoContrato estadoAnterior = null;
        Contrato contratoFinal = contrato;

        // 🔄 CONTROL DE EDICIÓN (Si el contrato ya existe en la BD)
        if (contrato.getId() != null) {
            Contrato contratoBD = contratoRepository.findById(contrato.getId())
                    .orElseThrow(() -> new RuntimeException("Contrato no encontrado con ID: " + contrato.getId()));
            
            estadoAnterior = contratoBD.getEstado();
            EstadoContrato nuevoEstado = contrato.getEstado();

            // 🛑 APLICAMOS LAS REGLAS DE NEGOCIO QUE TENÍAS EN cambiarEstado:
            
            // 1. Controlar que no intenten revivir un contrato
            if ((estadoAnterior == EstadoContrato.FINALIZADO || estadoAnterior == EstadoContrato.RESCINDIDO) 
                    && nuevoEstado == EstadoContrato.ACTIVO) {
                throw new IllegalArgumentException("¡Error! No se puede volver a ACTIVAR un contrato que ya está FINALIZADO o RESCINDIDO.");
            }
            
            // 2. Evitar volver a BORRADOR desde finalizado/rescindido
            if ((estadoAnterior == EstadoContrato.FINALIZADO || estadoAnterior == EstadoContrato.RESCINDIDO) 
                    && nuevoEstado == EstadoContrato.BORRADOR) {
                throw new IllegalArgumentException("¡Error! No se puede volver a BORRADOR un contrato que ya está FINALIZADO o RESCINDIDO.");
            }

            // 3. Controlar el paso de Borrador a Activo (un borrador solo puede pasar a ACTIVO)
            if (estadoAnterior == EstadoContrato.BORRADOR && nuevoEstado != EstadoContrato.ACTIVO && nuevoEstado != EstadoContrato.BORRADOR) {
                throw new IllegalArgumentException("Un contrato en BORRADOR solo puede pasar a estado ACTIVO.");
            }
            
            // 4. Controlar las salidas desde Activo
            if (estadoAnterior == EstadoContrato.ACTIVO 
                    && nuevoEstado != EstadoContrato.FINALIZADO 
                    && nuevoEstado != EstadoContrato.RESCINDIDO 
                    && nuevoEstado != EstadoContrato.ACTIVO) {
                throw new IllegalArgumentException("Un contrato ACTIVO solo puede pasar a FINALIZADO o RESCINDIDO.");
            }

            //  SI EL CONTRATO ERA BORRADOR: Actualizamos sus datos internos antes de cambiar el estado
            if (EstadoContrato.BORRADOR.equals(estadoAnterior)) {
                contratoBD.setPropiedad(contrato.getPropiedad());
                contratoBD.setInquilino(contrato.getInquilino());
                contratoBD.setFechaInicio(contrato.getFechaInicio());
                contratoBD.setDuracionMeses(contrato.getDuracionMeses());
                contratoBD.setImporteMensual(contrato.getImporteMensual());
                contratoBD.setDiaVencimientoMensual(contrato.getDiaVencimientoMensual());
            }

            //  LÓGICA DE DISPONIBILIDAD DE LA PROPIEDAD
            // Si pasa a ACTIVO, verificamos que no esté alquilada y cambiamos el estado de la propiedad
            if (nuevoEstado == EstadoContrato.ACTIVO && estadoAnterior != EstadoContrato.ACTIVO) {
                List<Contrato> activos = contratoRepository.findByPropiedadIdAndEstado(contratoBD.getPropiedad().getId(), EstadoContrato.ACTIVO);
                if (!activos.isEmpty()) {
                    throw new IllegalArgumentException("¡Error! La propiedad ya cuenta con un contrato ACTIVO vigente.");
                }
                if (contratoBD.getPropiedad() != null) {
                    contratoBD.getPropiedad().setEstadoDisponibilidad(EstadoDisponibilidad.ALQUILADA);
                    propiedadRepository.save(contratoBD.getPropiedad());
                }
            }
            
            // Si pasa a FINALIZADO o RESCINDIDO, la propiedad vuelve a estar DISPONIBLE
            if ((nuevoEstado == EstadoContrato.FINALIZADO || nuevoEstado == EstadoContrato.RESCINDIDO) && estadoAnterior != nuevoEstado) {
                if (contratoBD.getPropiedad() != null) {
                    contratoBD.getPropiedad().setEstadoDisponibilidad(EstadoDisponibilidad.DISPONIBLE);
                    propiedadRepository.save(contratoBD.getPropiedad());
                }
            }

            // Seteamos el estado final aprobado
            contratoBD.setEstado(nuevoEstado);
            contratoFinal = contratoBD;

        } else {
            // Si el contrato NO tiene ID, es un contrato NUEVO
            contrato.setEstado(EstadoContrato.BORRADOR);
        }

        // Guardamos el contrato con todos los datos y estados completamente validados
        Contrato contratoGuardado = contratoRepository.save(contratoFinal);

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
    	// 1. Buscamos el contrato. Si no existe, lanzamos excepción de inmediato.
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el contrato con ID: " + id));

        // 2. Traemos el estado (manejando si es ENUM o String)
        String estado = String.valueOf(contrato.getEstado());

        // 3. Validamos si es BORRADOR
        if ("BORRADOR".equalsIgnoreCase(estado)) {
            contratoRepository.deleteById(id);
        } else {
            // Si entra acá, SÍ O SÍ va a tirar la excepción
            throw new IllegalArgumentException("No se puede eliminar: El contrato se encuentra en estado: " + estado);
        }
    }
    

<<<<<<< HEAD
=======
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
>>>>>>> aa8ceaf638fa8874bf900fb4db114d0124cd0b45
}
	

