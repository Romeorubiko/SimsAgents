package ontologia.predicados;

import jadex.runtime.*;
import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;


public class HasCocinadoBarbacoa extends Predicado {

    private Higiene higiene;
    private Hambre hambre;
    private Diversion diversion;
    private Cocina cocina;

    public HasCocinadoBarbacoa() {
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
        this.hambre= hambre;
    }

    public Cocina getCocina() {
        return cocina;
    }

    public void setCocina(Cocina cocina) {
        this.cocina = cocina;
    }
}
