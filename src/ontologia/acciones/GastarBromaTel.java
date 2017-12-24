package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.habilidades.Carisma;
import ontologia.conceptos.necesidades.*;

public class GastarBromaTel extends Accion {

    private Diversion diversion;
    private Energia energia;
    private Carisma carisma;

    public GastarBromaTel() {
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion d) {
        diversion = d;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    // Se necesita porque el carisma determina la propabilidad de que falle una broma telefónica. 
    // No hay método set porque no la acción no modifica el nivel de carisma.
	public Carisma getCarisma() {
		return carisma;
	}
}
