package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;


public class SentarseSilla extends Accion {
    private Energia energia;

    public SentarseSilla() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }
}

   