package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class CamaHecha extends Predicado {
    private Energia energia;
    private Higiene higiene;

    public CamaHecha(Energia energia, Higiene higiene) {
        this.energia = energia;
        this.higiene = higiene;
    }

    public CamaHecha() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene higiene) {
        this.higiene = higiene;
    }
}
