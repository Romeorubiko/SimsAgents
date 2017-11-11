package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;


public class HasBebido extends Predicado {

    private Energia energia;
    private Vejiga vejiga;
    private Diversion diversion;

    public HasBebido() {
    }

    public HasBebido(Energia energia, Vejiga vejiga, Diversion diversion) {
        this.energia = energia;
        this.vejiga = vejiga;
        this.diversion = diversion;
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
