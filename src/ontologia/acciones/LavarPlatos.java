package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class LavarPlatos extends Accion {

    private Energia energia;
    private Higiene higiene;
    private Diversion diversion;

    public LavarPlatos() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene h) {
        higiene = h;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion d) {
        diversion = d;
    }
}
