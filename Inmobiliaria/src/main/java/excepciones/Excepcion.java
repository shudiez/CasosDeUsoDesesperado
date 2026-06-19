package excepciones;

public class Excepcion extends Exception {

	private String atributo;

	public Excepcion(String mensaje) {
		super(mensaje);
	}

	public Excepcion(String mensaje, String atributo) {
		super(mensaje);
		this.atributo = atributo;
	}

	public String getAtributo() {
		return atributo;
	}
}