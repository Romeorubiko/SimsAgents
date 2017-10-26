package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class FotoRealizada extends Predicado {
    private Energia energia;
    private Diversion diversion;
    private Fotografia fotografia;

    public FotoRealizada() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion diversion) {
        this.diversion = diversion;
    }

    public Fotografia getFotografia() {
        return fotografia;
    }

    public void setFotografia(Fotografia fotografia) {
        this.fotografia = fotografia;
    }
}
