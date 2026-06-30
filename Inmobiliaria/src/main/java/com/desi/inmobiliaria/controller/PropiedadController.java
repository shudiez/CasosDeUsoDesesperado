package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;
import com.desi.inmobiliaria.service.CiudadService;
import com.desi.inmobiliaria.service.PersonaService;
import com.desi.inmobiliaria.service.PropiedadService;
import com.desi.inmobiliaria.service.ProvinciaService;
import excepciones.Excepcion;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PropiedadController {

   // Servicios que necesito para trabajar con las propiedades
   @Autowired
   private PropiedadService propiedadService;

   @Autowired
   private CiudadService ciudadService;

   @Autowired
   private ProvinciaService provinciaService;

   @Autowired
   private PersonaService personaService;

   public PropiedadController() {
   }

   // Abre el formulario para crear una propiedad
   @GetMapping("/propiedad/nueva")
   public String nuevaPropiedad(Model model) {

      // Creo un formulario vacío
      model.addAttribute("propiedadForm", new PropiedadForm());

      // Cargo todos los datos de los combos
      model.addAttribute("tipos", TipoPropiedad.values());
      model.addAttribute("estados", EstadoDisponibilidad.values());
      model.addAttribute("ciudades", ciudadService.listarTodas());
      model.addAttribute("provincias", provinciaService.listarTodas());
      model.addAttribute("propietarios", personaService.listarTodas());

      return "propiedad-form";
   }

   // Elimina una propiedad
   @GetMapping("/propiedad/eliminar/{id}")
   public String eliminar(@PathVariable Long id, Model model) {

      try {

         // Intento eliminar la propiedad
         propiedadService.eliminar(id);

         return "redirect:/propiedades";

      } catch (Excepcion e) {

         // Si no se puede eliminar muestro el motivo
         model.addAttribute("error", e.getMessage());

         // Cargo nuevamente el listado
         model.addAttribute("propiedades", propiedadService.listarTodas());
         model.addAttribute("ciudades", ciudadService.listarTodas());
         model.addAttribute("tipos", TipoPropiedad.values());
         model.addAttribute("estados", EstadoDisponibilidad.values());

         return "propiedad-list";
      }
   }

   // Busca una propiedad para editarla
   @GetMapping("/propiedad/editar/{id}")
   public String editarPropiedad(@PathVariable Long id, Model model) {

      // Busco la propiedad
      Propiedad propiedad = propiedadService.buscarPorId(id);

      // Paso la propiedad al formulario
      PropiedadForm form = PropiedadForm.fromPojo(propiedad);

      model.addAttribute("propiedadForm", form);

      // Cargo nuevamente los combos
      model.addAttribute("tipos", TipoPropiedad.values());
      model.addAttribute("estados", EstadoDisponibilidad.values());
      model.addAttribute("ciudades", ciudadService.listarTodas());
      model.addAttribute("provincias", provinciaService.listarTodas());
      model.addAttribute("propietarios", personaService.listarTodas());

      return "propiedad-form";
   }

   // Muestra todas las propiedades
   @GetMapping("/propiedades")
   public String listar(Model model) {

      // Traigo todas las propiedades
      model.addAttribute("propiedades", propiedadService.listarTodas());

      // Cargo los filtros
      model.addAttribute("ciudades", ciudadService.listarTodas());
      model.addAttribute("tipos", TipoPropiedad.values());
      model.addAttribute("estados", EstadoDisponibilidad.values());

      return "propiedad-list";
   }

   // Guarda una propiedad nueva o editada
   @PostMapping("/propiedades")
   public String guardar(@ModelAttribute("propiedadForm") @Valid PropiedadForm propiedadForm,
                         BindingResult result,
                         Model model) {

      // Si falta completar algún dato vuelvo al formulario
      if (result.hasErrors()) {

         // Cargo otra vez los combos
         model.addAttribute("tipos", TipoPropiedad.values());
         model.addAttribute("estados", EstadoDisponibilidad.values());
         model.addAttribute("ciudades", ciudadService.listarTodas());
         model.addAttribute("provincias", provinciaService.listarTodas());
         model.addAttribute("propietarios", personaService.listarTodas());

         return "propiedad-form";

      } else {

         try {

            // Paso los datos del formulario a una Propiedad
            Propiedad propiedad = propiedadForm.toPojo();

            // La guardo
            propiedadService.guardar(propiedad);

            return "redirect:/propiedades";

         } catch (Excepcion e) {

            // Si hay un error lo muestro
            model.addAttribute("error", e.getMessage());

            // Mantengo los datos que escribió el usuario
            model.addAttribute("propiedadForm", propiedadForm);

            // Cargo nuevamente los combos
            model.addAttribute("tipos", TipoPropiedad.values());
            model.addAttribute("estados", EstadoDisponibilidad.values());
            model.addAttribute("ciudades", ciudadService.listarTodas());
            model.addAttribute("provincias", provinciaService.listarTodas());
            model.addAttribute("propietarios", personaService.listarTodas());

            return "propiedad-form";
         }
      }
   }

   // Busca propiedades usando los filtros
   @GetMapping("/propiedades/buscar")
   public String buscar(@RequestParam(required = false) String direccion,
                        @RequestParam(required = false) Long ciudadId,
                        @RequestParam(required = false) TipoPropiedad tipo,
                        @RequestParam(required = false) EstadoDisponibilidad estado,
                        Model model) {

      // Traigo solo las propiedades que cumplen con los filtros
      model.addAttribute("propiedades",
              propiedadService.buscarConFiltros(direccion, ciudadId, tipo, estado));

      // Cargo nuevamente los filtros
      model.addAttribute("ciudades", ciudadService.listarTodas());
      model.addAttribute("tipos", TipoPropiedad.values());
      model.addAttribute("estados", EstadoDisponibilidad.values());

      return "propiedad-list";
   }
}
