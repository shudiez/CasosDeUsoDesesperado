package excepciones;

public class EntidadNoEncontradaException extends RuntimeException {

	public EntidadNoEncontradaException(String entidad, Long id) {
		super("No se encontró " + entidad + " con id: " + id);
	}
}
