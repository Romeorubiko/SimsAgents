package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;


public class PerritosQuemados extends Predicado {

    private Higiene higiene;
    private Hambre hambre;
    private Diversion diversion;
    private Cocina cocina;

    public PerritosQuemados() {
    }

    public PerritosQuemados(Higiene higiene, Hambre hambre, Diversion diversion, Cocina cocina) {
        this.higiene = higiene;
        this.hambre = hambre;
        this.diversion = diversion;
        this.cocina = cocina;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene higiene) {
        this.higiene = higiene;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion diversion) {
        this.diversion = diversion;
    }

    public Hambre getHambre() {
        return hambre;
    }

    public void setHambre(Hambre hambre) {
        this.hambre = hambre;
    }

    public Cocina getCocina() {
        return cocina;
    }

    public void setCocina(Cocina cocina) {
        this.cocina = cocina;
    }
}
