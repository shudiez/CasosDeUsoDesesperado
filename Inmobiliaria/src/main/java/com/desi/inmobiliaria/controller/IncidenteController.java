@Controller
public class IncidenteController {

   // Servicio que se encarga de los incidentes
   @Autowired
   private IncidenteService incidenteService;

   // Lo uso para traer las propiedades
   @Autowired
   private PropiedadService propiedadService;

   public IncidenteController() {
   }

   // Abre el formulario para cargar un incidente nuevo
   @GetMapping("/incidente/nuevo")
   public String nuevoIncidente(Model model) {

      // Creo un incidente vacío
      model.addAttribute("incidente", new Incidente());

      // Cargo los datos de los combos del formulario
      model.addAttribute("categorias", CategoriaIncidente.values());
      model.addAttribute("prioridades", PrioridadIncidente.values());
      model.addAttribute("estados", EstadoIncidente.values());

      // Cargo las propiedades para poder elegir una
      model.addAttribute("propiedades", propiedadService.listarTodas());

      return "incidente-form";
   }

   // Guarda el incidente
   @PostMapping("/incidentes")
   public String guardar(Incidente incidente) {

      // Lo mando al service para guardarlo
      incidenteService.guardar(incidente);

      // Cuando termina vuelvo al listado
      return "redirect:/incidentes";
   }

   // Muestra todos los incidentes
   @GetMapping("/incidentes")
   public String listar(Model model) {

      // Traigo todos los incidentes y los envío a la vista
      model.addAttribute("incidentes", incidenteService.listarTodos());

      return "incidente-list";
   }

   // Busca un incidente para poder editarlo
   @GetMapping("/incidente/editar/{id}")
   public String editar(@PathVariable Long id, Model model) {

      // Busco el incidente por su id
      model.addAttribute("incidente", incidenteService.buscarPorId(id));

      // Cargo nuevamente los datos de los combos
      model.addAttribute("categorias", CategoriaIncidente.values());
      model.addAttribute("prioridades", PrioridadIncidente.values());
      model.addAttribute("estados", EstadoIncidente.values());

      // Cargo las propiedades
      model.addAttribute("propiedades", propiedadService.listarTodas());

      return "incidente-form";
   }

   // Elimina un incidente
   @GetMapping("/incidente/eliminar/{id}")
   public String eliminar(@PathVariable Long id) {

      // Lo elimino usando el id
      incidenteService.eliminar(id);

      // Después vuelvo al listado
      return "redirect:/incidentes";
   }
}
