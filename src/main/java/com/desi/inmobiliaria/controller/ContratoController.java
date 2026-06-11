package com.desi.inmobiliaria.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.desi.entity.Contrato;
import com.desi.entity.EstadoContrato;
import com.desi.service.ContratoService;
import com.desi.service.PropiedadService;
import com.desi.service.PersonaService;

import jakarta.validation.Valid;


@Controller
public class ContratoController {

    // Dependencias declaradas como 'final' (Buenas prácticas)
    private final ContratoService contratoService;
    private final PropiedadService propiedadService; 
    private final PersonaService personaService;
    
    // Inyección por constructor (Sin necesidad de @Autowired)
    public ContratoController(ContratoService contratoService, PropiedadService propiedadService, PersonaService personaService) {
        this.contratoService = contratoService;
        this.propiedadService = propiedadService;
        this.personaService = personaService;
    }

    // 1. NUEVO CONTRATO (Muestra el formulario)
    
    @GetMapping("/contrato/nuevo")
    public String nuevoContrato(Model model) {
    	Contrato contrato= new Contrato();
    	
        model.addAttribute("contrato", contrato);
        // Tip Pro: Usamos el servicio de propiedades para que el usuario pueda 
        // seleccionar qué propiedad está asociando a este contrato en un desplegable
        model.addAttribute("propiedades", propiedadService.listarTodas());
        model.addAttribute("inquilinos", personaService.listarTodas()); // <-- Necesitás este servicio
        model.addAttribute("estados", EstadoContrato.values()); // Envía las opciones del Enum
        
               return "contrato-form";
    }

    // 2. GUARDAR CONTRATO
    @PostMapping("/contratos")
    public String guardar(@Valid @ModelAttribute("contrato") Contrato contrato, BindingResult result, Model model) {
    	//System.out.println("ID DEL CONTRATO RECIBIDO: " + contrato.getId());
        // Si hay errores, volvemos a cargar las listas para los desplegables
        if (result.hasErrors()) {
            model.addAttribute("propiedades", propiedadService.listarTodas());
            model.addAttribute("inquilinos", personaService.listarTodas());
            model.addAttribute("estados", EstadoContrato.values());
            return "contrato-form"; 
        }
        // Si es un contrato nuevo, le agregamos el estado inicial a tu historial
        if (contrato.getId() == null && contrato.getEstado() != null) {
            contrato.agregarCambioEstado(contrato.getEstado());
        }
        
        try {
        	  contratoService.guardar(contrato);
        	  return "redirect:/contratos";
        } catch(RuntimeException e) {
        	
        	result.rejectValue("propiedad", "error.propiedad", e.getMessage());
        	
        	model.addAttribute("propiedades", propiedadService.listarTodas());
        	model.addAttribute("inquilinos", personaService.listarTodas());
        	model.addAttribute("estados", EstadoContrato.values());
        	
        	return "contrato-form";
        }
   
        
    }

    /*
    // 3. LISTAR CONTRATOS
    @GetMapping("/contratos")
    public String listar(Model model) {
        model.addAttribute("contratos", contratoService.listarTodos());
        return "contrato-list";
    }
    */
    
    //3.1 listar con filtros.
    @GetMapping("/contratos")
    public String listarContratos(
            @RequestParam(value = "propiedad", required = false) String propiedad,
            @RequestParam(value = "inquilino", required = false) String inquilino,
            @RequestParam(value = "estado", required = false) EstadoContrato estado,
            @RequestParam(value = "fechaInicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
            Model model) {
        
        // Llamamos al servicio con los filtros (pueden venir cargados o null)
        List<Contrato> listaFiltrada = contratoService.listarConFiltros(propiedad, inquilino, estado, fechaInicio);
        
        // Mandamos la lista a la tabla
        model.addAttribute("contratos", listaFiltrada);
        
        // Volvemos a mandar los estados para el desplegable del buscador
        model.addAttribute("estados", EstadoContrato.values()); 
        
        // Devolvemos los datos que usó el usuario para que queden escritos en los inputs del buscador
        model.addAttribute("propiedadBuscada", propiedad);
        model.addAttribute("inquilinoBuscado", inquilino);
        model.addAttribute("estadoBuscado", estado);
        model.addAttribute("fechaBuscada", fechaInicio);

        return "contrato-list";
    }

    
    // 4. EDITAR CONTRATO
    @GetMapping("/contrato/editar/{id}")
    public String editarContrato(@PathVariable Long id, Model model) {
        Contrato contrato = contratoService.buscarPorId(id);
        model.addAttribute("contrato", contrato);
        
        // También pasamos las propiedades por si quiere cambiar la propiedad del contrato, inquilino y estado
        model.addAttribute("propiedades", propiedadService.listarTodas());
        model.addAttribute("inquilinos", personaService.listarTodas());
        model.addAttribute("estados", EstadoContrato.values());
        return "contrato-form";
    }

    
    
    // 5. ELIMINAR CONTRATO
    @GetMapping("/contrato/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        contratoService.eliminar(id);
        return "redirect:/contratos";
    }
}