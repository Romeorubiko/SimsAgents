
package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;


public class Reparar extends Accion {

    private Higiene higiene;
    private Energia energia;
    private Mecanica mecanica;

    public Reparar() {
    }


    public Reparar(Higiene higiene, Energia energia, Mecanica mecanica) {
		super();
		this.higiene = higiene;
		this.energia = energia;
		this.mecanica = mecanica;
	}


	public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene higiene) {
        this.higiene = higiene;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Mecanica getMecanica() {
        return mecanica;
    }

    public void setMecanica(Mecanica mecanica) {
        this.mecanica = mecanica;
    }
}
