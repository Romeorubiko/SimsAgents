package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class ComerComida extends Accion {


    public ComerComida(Energia energia, Hambre hambre) {
		super();
		this.energia = energia;
		this.hambre = hambre;
	}

	private Energia energia;
    private Hambre hambre;

    public ComerComida() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public Hambre getHambre() {
        return hambre;
    }

    public void setHambre(Hambre c) {
        hambre = c;
    }

}
