package com.desi.inmobiliaria.controller;

import com.desi.inmobiliaria.entity.Persona;
import com.desi.inmobiliaria.service.PersonaService;
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
public class PersonaController {

	// Servicio que uso para trabajar con las personas
	@Autowired
	private PersonaService personaService;

	public PersonaController() {
	}

	// Muestra el listado de personas
	@GetMapping("/personas")
	public String listar(Model model) {

		// Traigo todas las personas y las envío a la vista
		model.addAttribute("personas", personaService.listarTodas());

		return "persona-list";
	}

	// Abre el formulario para crear una persona
	@GetMapping("/persona/nueva")
	public String nuevaPersona(Model model) {

		// Creo un formulario vacío
		model.addAttribute("personaForm", new PersonaForm());

		return "persona-form";
	}

	// Guarda una persona
	@PostMapping("/personas")
	public String guardar(@ModelAttribute("personaForm") @Valid PersonaForm personaForm,
			BindingResult result, Model model) {

		// Si falta completar algún dato vuelvo al formulario
		if (result.hasErrors()) {
			return "persona-form";
		} else {

			try {

				// Paso los datos del formulario a un objeto Persona
				Persona persona = personaForm.toPojo();

				// Guardo la persona
				personaService.guardar(persona);

				// Si salió bien vuelvo al listado
				return "redirect:/personas";

			} catch (RuntimeException e) {

				// Si ocurre un error lo muestro en pantalla
				model.addAttribute("error", e.getMessage());

				return "persona-form";
			}
		}
	}

	// Busca una persona para poder editarla
	@GetMapping("/persona/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {

		// Busco la persona por el id
		Persona persona = personaService.buscarPorId(id);

		// Paso la persona al formulario para mostrar sus datos
		PersonaForm form = PersonaForm.fromPojo(persona);

		model.addAttribute("personaForm", form);

		return "persona-form";
	}

	// Elimina una persona
	@GetMapping("/persona/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model model) {

		try {

			// Intento eliminar la persona
			personaService.eliminar(id);

			return "redirect:/personas";

		} catch (RuntimeException e) {

			// Si no se puede eliminar, muestro el motivo
			model.addAttribute("error", e.getMessage());

			// Cargo nuevamente el listado
			model.addAttribute("personas", personaService.listarTodas());

			return "persona-list";
		}
	}
}
