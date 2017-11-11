package ontologia.predicados;


import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;


public class LavaboEstropeadoBeber extends Predicado {
    private Energia energia;
    private Vejiga vejiga;
    private Diversion diversion;

    public LavaboEstropeadoBeber() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public Vejiga getVejiga() {
        return vejiga;
    }

    public void setVejiga(Vejiga c) {
        vejiga = c;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion d) {
        diversion = d;
    }
}
