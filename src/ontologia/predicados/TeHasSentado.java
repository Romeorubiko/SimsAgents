package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class TeHasSentado extends Predicado {
    private Energia energia;

    public TeHasSentado() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }
}
