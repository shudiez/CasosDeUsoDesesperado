package com.desi.inmobiliaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.desi.entity.Contrato;
import com.desi.service.ContratoService;
import com.desi.service.PropiedadService;



@Controller
public class ContratoController {

    // Dependencias declaradas como 'final' (Buenas prácticas)
    private final ContratoService contratoService;
    private final PropiedadService propiedadService; 

    // Inyección por constructor (Sin necesidad de @Autowired)
    public ContratoController(ContratoService contratoService, PropiedadService propiedadService) {
        this.contratoService = contratoService;
        this.propiedadService = propiedadService;
    }

    // 1. NUEVO CONTRATO (Muestra el formulario)
    @GetMapping("/contrato/nuevo")
    public String nuevoContrato(Model model) {
        model.addAttribute("contrato", new Contrato());
        
        // Tip Pro: Usamos el servicio de propiedades para que el usuario pueda 
        // seleccionar qué propiedad está asociando a este contrato en un desplegable
        model.addAttribute("propiedades", propiedadService.listarTodas());
        
        return "contrato-form";
    }

    // 2. GUARDAR CONTRATO
    @PostMapping("/contratos")
    public String guardar(Contrato contrato) {
        contratoService.guardar(contrato);
        return "redirect:/contratos";
    }

    // 3. LISTAR CONTRATOS
    @GetMapping("/contratos")
    public String listar(Model model) {
        model.addAttribute("contratos", contratoService.listarTodos());
        return "contrato-list";
    }

    // 4. EDITAR CONTRATO
    @GetMapping("/contrato/editar/{id}")
    public String editarContrato(@PathVariable Long id, Model model) {
        Contrato contrato = contratoService.buscarPorId(id);
        model.addAttribute("contrato", contrato);
        
        // También pasamos las propiedades por si quiere cambiar la propiedad del contrato
        model.addAttribute("propiedades", propiedadService.listarTodas());
        
        return "contrato-form";
    }

    // 5. ELIMINAR CONTRATO
    @GetMapping("/contrato/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        contratoService.eliminar(id);
        return "redirect:/contratos";
    }
}