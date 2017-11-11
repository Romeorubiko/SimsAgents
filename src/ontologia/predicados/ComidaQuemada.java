package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;


public class ComidaQuemada extends Predicado {

    private Energia energia;
    private Cocina cocina;
    private Hambre hambre;

    public ComidaQuemada() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public Cocina getCocina() {
        return cocina;
    }

    public void setCocina(Cocina c) {
        cocina = c;
    }

    public Hambre getHambre() {
        return hambre;
    }

    public void setHambre(Hambre h) {
        hambre = h;
    }

}
