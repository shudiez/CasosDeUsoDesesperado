package com.desi.inmobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Publicacion;
import com.desi.inmobiliaria.repository.PublicacionesRepository;
import com.desi.inmobiliaria.service.PublicacionService;

import java.time.LocalDate;

@Controller
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PublicacionesRepository publicacionesRepository;

 
    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model) {
        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setFechaPublicacion(LocalDate.now());
        
        model.addAttribute("publicacion", nuevaPublicacion);
        model.addAttribute("propiedadesDisponibles", publicacionesRepository.buscarPropiedadesDisponibles(EstadoDisponibilidad.DISPONIBLE));
       // model.addAttribute("propiedadesDisponibles", publicacionesRepository.buscarPropiedadesDisponibles(EstadoDisponibilidad.RESERVADA));
        model.addAttribute("listaPublicaciones", publicacionesRepository.findByEliminadaFalse());
        
        return "publicaciones";
    }

    
    @PostMapping("/alta")
    public String guardarPublicacion(@ModelAttribute("publicacion") Publicacion publicacion, Model model, RedirectAttributes redirectAttributes) {
        try {
            publicacionService.crearPublicacion(publicacion);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Publicación creada con éxito!");
            return "redirect:/publicaciones/alta"; 
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("propiedadesDisponibles", publicacionesRepository.buscarPropiedadesDisponibles(EstadoDisponibilidad.DISPONIBLE));
            model.addAttribute("listaPublicaciones", publicacionesRepository.findByEliminadaFalse());
            return "publicaciones";
        }
    }

    
    @PostMapping("/eliminar/{id}")
    public String eliminarPublicacion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            publicacionService.eliminarPublicacion(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "La publicación fue dada de baja correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/publicaciones/alta";
    }

    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Publicacion publicacion = publicacionesRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Publicación no encontrada"));
        
   
        model.addAttribute("publicacion", publicacion);
        
        
        model.addAttribute("propiedadesDisponibles", publicacionesRepository.buscarPropiedadesDisponibles(EstadoDisponibilidad.DISPONIBLE));
        model.addAttribute("listaPublicaciones", publicacionesRepository.findByEliminadaFalse());
        
        
        return "publicaciones";
    }

 
    @PostMapping("/editar/{id}")
    public String guardarEdicion(@PathVariable Long id, @ModelAttribute("publicacion") Publicacion publicacion, RedirectAttributes redirectAttributes, Model model) {
        try {
            publicacionService.modificarPublicacion(id, publicacion);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Publicación modificada con éxito!");
      
            return "redirect:/publicaciones/alta"; 
            
        } catch (IllegalArgumentException | IllegalStateException e) {
            Publicacion original = publicacionesRepository.findById(id).get();
            publicacion.setPropiedad(original.getPropiedad());
            
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("propiedadesDisponibles", publicacionesRepository.buscarPropiedadesDisponibles(EstadoDisponibilidad.DISPONIBLE));
            model.addAttribute("listaPublicaciones", publicacionesRepository.findByEliminadaFalse());
            
            return "publicaciones";
        }
    }
}