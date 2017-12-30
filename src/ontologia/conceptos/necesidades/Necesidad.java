package ontologia.conceptos.necesidades;

import ontologia.Concepto;

public abstract class Necesidad extends Concepto {
	public static final int NC_POCO = 1;
	public static final int NC_NORMAL = 3;
	public static final int NC_MUCHO = 5;
	private int grado;
	public Necesidad(){
		this.grado=100;
	}
	public int getGrado() {
		return grado;
	}

	public void setGrado(int grado) {
		if (grado >100) {
			this.grado = 100;
		}
		else {
			this.grado = grado;
		}
	}

	
}