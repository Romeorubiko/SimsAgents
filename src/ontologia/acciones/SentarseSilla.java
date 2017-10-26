package ontologia.acciones;

import jadex.runtime.*;
import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;


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

   