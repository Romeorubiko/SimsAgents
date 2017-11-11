package ontologia.predicados;


import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;


public class NeveraEstropeadaComer extends Predicado {
    private Energia energia;
    private Hambre hambre;

    public NeveraEstropeadaComer() {
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
