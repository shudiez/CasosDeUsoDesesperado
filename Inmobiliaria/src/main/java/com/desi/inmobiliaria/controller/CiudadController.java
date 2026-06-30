package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Ciudad;
import com.desi.inmobiliaria.service.CiudadService;
import com.desi.inmobiliaria.service.ProvinciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CiudadController {

   // Inyecto el servicio que se encarga de todas las operaciones de Ciudad
   @Autowired
   private CiudadService ciudadService;

   // Inyecto el servicio de Provincia para poder cargar el combo en el formulario
   @Autowired
   private ProvinciaService provinciaService;

   public CiudadController() {
   }

   // Muestra el listado de ciudades
   @GetMapping({"/ciudades"})
   public String listar(Model model) {

      // Obtengo todas las ciudades y las mando a la vista
      model.addAttribute("ciudades", this.ciudadService.listarTodas());

      // Devuelvo la página con el listado
      return "ciudad-list";
   }

   // Abre el formulario para crear una nueva ciudad
   @GetMapping({"/ciudad/nueva"})
   public String nuevaCiudad(Model model) {

      // Creo un formulario vacío
      model.addAttribute("ciudadForm", new CiudadForm());

      // Cargo las provincias para mostrarlas en el select
      model.addAttribute("provincias", this.provinciaService.listarTodas());

      // Muestro el formulario
      return "ciudad-form";
   }

   // Guarda una ciudad nueva o editada
   @PostMapping({"/ciudades"})
   public String guardar(@ModelAttribute("ciudadForm") @Valid CiudadForm ciudadForm,
                         BindingResult result,
                         Model model) {

      // Si hay errores de validación vuelvo al formulario
      if (result.hasErrors()) {

         // Cargo otra vez las provincias porque el formulario las necesita
         model.addAttribute("provincias", this.provinciaService.listarTodas());

         return "ciudad-form";

      } else {

         try {

            // Paso los datos del formulario al objeto Ciudad
            Ciudad ciudad = ciudadForm.toPojo();

            // Guardo la ciudad
            this.ciudadService.guardar(ciudad);

            // Si salió bien vuelvo al listado
            return "redirect:/ciudades";

         } catch (RuntimeException e) {

            // Si ocurre algún error del servicio lo muestro en pantalla
            model.addAttribute("error", e.getMessage());

            // Cargo nuevamente las provincias
            model.addAttribute("provincias", this.provinciaService.listarTodas());

            return "ciudad-form";
         }
      }
   }

   // Busca una ciudad por su id y carga sus datos para editarla
   @GetMapping({"/ciudad/editar/{id}"})
   public String editarCiudad(@PathVariable Long id, Model model) {

      // Busco la ciudad
      Ciudad ciudad = this.ciudadService.buscarPorId(id);

      // Convierto la entidad al formulario
      CiudadForm form = CiudadForm.fromPojo(ciudad);

      // Envío los datos a la vista
      model.addAttribute("ciudadForm", form);

      // Cargo nuevamente las provincias para el select
      model.addAttribute("provincias", this.provinciaService.listarTodas());

      return "ciudad-form";
   }

   // Elimina una ciudad si cumple las validaciones del sistema
   @GetMapping({"/ciudad/eliminar/{id}"})
   public String eliminarCiudad(@PathVariable Long id, Model model) {

      try {

         // Intento eliminar la ciudad
         this.ciudadService.eliminar(id);

         // Si salió bien vuelvo al listado
         return "redirect:/ciudades";

      } catch (RuntimeException e) {

         // Si no se puede eliminar, muestro el motivo
         model.addAttribute("error", e.getMessage());

         // Recargo el listado de ciudades
         model.addAttribute("ciudades", this.ciudadService.listarTodas());

         return "ciudad-list";
      }
   }
}
