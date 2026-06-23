package com.desi.inmobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.desi.inmobiliaria.entity.EstadoDisponibilidad;
import com.desi.inmobiliaria.entity.Propiedad;
import com.desi.inmobiliaria.entity.TipoPropiedad;
import com.desi.inmobiliaria.service.CiudadService;
import com.desi.inmobiliaria.service.PersonaService;
import com.desi.inmobiliaria.service.PropiedadService;

import excepciones.Excepcion;
import jakarta.validation.Valid;

@Controller
public class PropiedadController {

	// Permite que Spring me proporcione automaticamente una instancia de
	// PropiedadService
	@Autowired
	private PropiedadService propiedadService;

	// Permite que Spring me proporcione automaticamente una instancia de
	// CiudadService
	@Autowired
	private CiudadService ciudadService;

	//// Permite que Spring me proporcione automaticamente una instancia de
	// PersonaSercice
	@Autowired
	private PersonaService personaService;

	// ALTA DE UNA PROPIEDAD
	// Abre el formulario para cargar una nueva propiedad
	@GetMapping("/propiedad/nueva")
	public String nuevaPropiedad(Model model) {

		// Creo un formulario vacio para que el usuario compelte los datos.
		model.addAttribute("propiedadForm", new PropiedadForm());

		// Cargo todos los tipos de prop para mostrarlos en el menu desplegable
		model.addAttribute("tipos", TipoPropiedad.values());

		// Cargo todos los estados de disponibilidad para mostrarlos en el menu
		model.addAttribute("estados", EstadoDisponibilidad.values());

		// Cargo todas las ciudades para que el usuario pueda elegir una.
		model.addAttribute("ciudades", ciudadService.listarTodas());

		// Cargo todos los propietarios para que el usuario pueda seleccionar uno
		model.addAttribute("propietarios", personaService.listarTodas());

		// Le digo a Spring que muestre la vista propiedad-form
		return "propiedad-form";
	}

	// ELIMINACION DE UNA PROPIEDAD
	// Elimina una propiedad segun su ID
	@GetMapping("/propiedad/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model model) {

		try {
			// le pido al Service que elimine la prop
			propiedadService.eliminar(id);

			// Si la eliminacion fue exitosa, vuelvo al listado de propiedades
			return "redirect:/propiedades";

			// Si la propiedad no puede eliminarse, por ejemplo porque tiene un contrato
			// activo, muestro el mensaje de error y vuelvo al listado.
		} catch (Excepcion e) {

			// Guardo el mensaje de error para mostrarlo en la vista
			model.addAttribute("error", e.getMessage());

			// Cargo nuevamente todas las propiedades para mostrar el listado
			model.addAttribute("propiedades", propiedadService.listarTodas());

			// Cargo las ciudades para los filtros de busqueda
			model.addAttribute("ciudades", ciudadService.listarTodas());

			// Cargo los tipos de propiedad para los filtros
			model.addAttribute("tipos", TipoPropiedad.values());

			// Cargo los estados de disponibilidad para los filtros
			model.addAttribute("estados", EstadoDisponibilidad.values());

			// Vuelvo al listado mostrando el error
			return "propiedad-list";
		}
	}

	// MODIFICACION DE UNA PROPIEDAD
	// Busca una propiedad por ID y carga sus datos para poder modificarla
	@GetMapping("/propiedad/editar/{id}")
	public String editarPropiedad(@PathVariable Long id, Model model) {

		// Busco la propiedad en la base de datos.
		Propiedad propiedad = propiedadService.buscarPorId(id);

		// Convierto la entidad Propiedad en un PropiedadForm para mostrar los datos en
		// el formulario.
		PropiedadForm form = PropiedadForm.fromPojo(propiedad);

		// Envio el formulario ya cargado a la vista.
		model.addAttribute("propiedadForm", form);

		// Cargo todos los tipos de propiedad para mostrarlos en el menu desplegable
		model.addAttribute("tipos", TipoPropiedad.values());

		// Cargo todos los estados de disponibilidad para mostrarlos en el menu
		model.addAttribute("estados", EstadoDisponibilidad.values());

		// Cargo todas las ciudades para que el usuario pueda seleccionar una
		model.addAttribute("ciudades", ciudadService.listarTodas());

		// Cargo todos los propietarios para que el usuario pueda seleccionar uno
		model.addAttribute("propietarios", personaService.listarTodas());

		// Devuelvo la misma vista del formulario, pero con los datos ya cargados
		return "propiedad-form";
	}

	// LISTADO DE PROPIEDADES
	// Muestra todas las propiedades registradas
	@GetMapping("/propiedades")
	public String listar(Model model) {

		// Pido al Service todas las propiedades y las envio a la vista.
		model.addAttribute("propiedades", propiedadService.listarTodas());

		// Cargo todas las ciudades para el filtro de busqueda
		model.addAttribute("ciudades", ciudadService.listarTodas());

		// Cargo todos los tipos de propiedad para el filtro
		model.addAttribute("tipos", TipoPropiedad.values());

		// Cargo todos los estados de disponibilidad para el filtro
		model.addAttribute("estados", EstadoDisponibilidad.values());

		// Devuelvo la vista que muestra el listado de propiedades
		return "propiedad-list";
	}

	// GUARDAR PROPIEDAD
	// Guarda una nueva propiedad o actualiza una existente
	@PostMapping("/propiedades")

	// Con @Valid le digo a Spring que valide automáticamente los datos del
	// formulario usando las reglas que puse en PropiedadForm.
	// Con BindingResult guardo los errores de validacion.
	public String guardar(@Valid @ModelAttribute("propiedadForm") PropiedadForm propiedadForm, BindingResult result,
			Model model) {

		// Si hay errores, vuelvo a mostrar el formulario para que el usuario los
		// corrija.
		if (result.hasErrors()) {

			// Cargo de nuevo los tipos de propiedad para el menu desplegable
			model.addAttribute("tipos", TipoPropiedad.values());

			// Cargo de nuevo los estados para el menu desplegable
			model.addAttribute("estados", EstadoDisponibilidad.values());

			// Cargode nuevo las ciudades para que puedan seleccionarse
			model.addAttribute("ciudades", ciudadService.listarTodas());

			// Cargo de nuevo los propietarios para que puedan seleccionarse
			model.addAttribute("propietarios", personaService.listarTodas());

			// Vuelvo al formulario mostrando los errores encontrados
			return "propiedad-form";
		}

		try {
			// Convierto el formulario en una entidad Propiedad para poder guardarla.
			Propiedad propiedad = propiedadForm.toPojo();

			// Llamo al Service para que aplique las reglas de negocio y guarde la
			// propiedad.
			propiedadService.guardar(propiedad);

			// Si todo salio bien, vuelvo al listado de propiedades
			return "redirect:/propiedades";

			// Si pasa algun problema de logica, por ejemplo una propiedad duplicada,
			// capturo la excepcion y muestro el mensaje en pantalla.
		} catch (Excepcion e) {

			// Guardo el mensaje de error para mostrarlo en la vista
			model.addAttribute("error", e.getMessage());

			// Mantengo los datos que el usuario habia ingresado en el formulario
			model.addAttribute("propiedadForm", propiedadForm);

			// Cargo de nuevo los datos necesarios para los menues desplegables
			model.addAttribute("tipos", TipoPropiedad.values());
			model.addAttribute("estados", EstadoDisponibilidad.values());
			model.addAttribute("ciudades", ciudadService.listarTodas());
			model.addAttribute("propietarios", personaService.listarTodas());

			// Vuelvo al formulario mostrando el error
			return "propiedad-form";
		}
	}

	// BUSCAR / FILTRAR PROPIEDADES
	@GetMapping("/propiedades/buscar")
	public String buscar(@RequestParam(required = false) String direccion,
			@RequestParam(required = false) Long ciudadId, @RequestParam(required = false) TipoPropiedad tipo,
			@RequestParam(required = false) EstadoDisponibilidad estado, Model model) {

		// Busca propiedades aplicando los filtros seleccionados.
		// Los filtros que vengan vacíos serán ignorados.
		model.addAttribute("propiedades", propiedadService.buscarConFiltros(direccion, ciudadId, tipo, estado));

		// Cargo nuevamente las ciudades para el filtro
		model.addAttribute("ciudades", ciudadService.listarTodas());

		// Cargo nuevamente los tipos de propiedad para el filtro
		model.addAttribute("tipos", TipoPropiedad.values());

		// Cargo nuevamente los estados para el filtro
		model.addAttribute("estados", EstadoDisponibilidad.values());

		// Devuelvo la vista con el listado filtrado
		return "propiedad-list";
	}

}