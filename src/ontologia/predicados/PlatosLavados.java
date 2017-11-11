package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class PlatosLavados extends Predicado {

    private Energia energia;
    private Diversion diversion;
    private Higiene higiene;

    public PlatosLavados() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion d) {
        diversion = d;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene h) {
        higiene = h;
    }
}
