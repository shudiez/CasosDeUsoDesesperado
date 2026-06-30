package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Provincia;
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
public class ProvinciaController {

   // Servicio que uso para trabajar con las provincias
   @Autowired
   private ProvinciaService provinciaService;

   public ProvinciaController() {
   }

   // Muestra el listado de provincias
   @GetMapping("/provincias")
   public String listar(Model model) {

      // Traigo todas las provincias y las envío a la vista
      model.addAttribute("provincias", provinciaService.listarTodas());

      return "provincia-list";
   }

   // Abre el formulario para crear una provincia
   @GetMapping("/provincia/nueva")
   public String nuevaProvincia(Model model) {

      // Creo un formulario vacío
      model.addAttribute("provinciaForm", new ProvinciaForm());

      return "provincia-form";
   }

   // Guarda una provincia nueva o editada
   @PostMapping("/provincias")
   public String guardar(@ModelAttribute("provinciaForm") @Valid ProvinciaForm provinciaForm,
                         BindingResult result,
                         Model model) {

      // Si hay errores en los datos vuelvo al formulario
      if (result.hasErrors()) {

         return "provincia-form";

      } else {

         try {

            // Paso los datos del formulario a una Provincia
            Provincia provincia = provinciaForm.toPojo();

            // La guardo usando el service
            provinciaService.guardar(provincia);

            // Si salió bien vuelvo al listado
            return "redirect:/provincias";

         } catch (RuntimeException e) {

            // Si ocurre un error lo muestro en pantalla
            model.addAttribute("error", e.getMessage());

            // Mantengo los datos que escribió el usuario
            model.addAttribute("provinciaForm", provinciaForm);

            return "provincia-form";
         }
      }
   }

   // Busca una provincia para editarla
   @GetMapping("/provincia/editar/{id}")
   public String editar(@PathVariable Long id, Model model) {

      // Busco la provincia por el id
      Provincia provincia = provinciaService.buscarPorId(id);

      // Paso la provincia al formulario
      ProvinciaForm form = ProvinciaForm.fromPojo(provincia);

      model.addAttribute("provinciaForm", form);

      return "provincia-form";
   }

   // Elimina una provincia
   @GetMapping("/provincia/eliminar/{id}")
   public String eliminar(@PathVariable Long id, Model model) {

      try {

         // Intento eliminar la provincia
         provinciaService.eliminar(id);

         return "redirect:/provincias";

      } catch (RuntimeException e) {

         // Si no se puede eliminar muestro el motivo
         model.addAttribute("error", e.getMessage());

         // Cargo nuevamente el listado
         model.addAttribute("provincias", provinciaService.listarTodas());

         return "provincia-list";
      }
   }
}
