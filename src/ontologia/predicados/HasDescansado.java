package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class HasDescansado extends Predicado {

    private Energia energia;

    public HasDescansado() {
    }

    public HasDescansado(Energia energia) {
        this.energia = energia;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

}
