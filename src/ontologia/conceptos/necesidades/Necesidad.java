package ontologia.conceptos.necesidades;

import ontologia.Concepto;

public abstract class Necesidad extends Concepto {
	public static final int NC_POCO = 4;
	public static final int NC_NORMAL = 6;
	public static final int NC_MUCHO = 8;
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
		else if (grado < 0){
			this.grado = 0;
		}
		else {
			this.grado = grado;
		}
	}

	
}