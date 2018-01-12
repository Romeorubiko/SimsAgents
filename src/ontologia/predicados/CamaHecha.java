package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class CamaHecha extends Predicado {

    private Higiene higiene;

    public CamaHecha(Higiene higiene) {
        this.higiene = higiene;
    }

    public CamaHecha() {
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene higiene) {
        this.higiene = higiene;
    }
}
