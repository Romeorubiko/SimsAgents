package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class EspejoUsado extends Predicado {
    private Energia energia;
    private Carisma carisma;


    public EspejoUsado() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

   public Carisma getCarisma() {
        return carisma;
    }

    public void setCarisma(Carisma carisma) {
        this.carisma = carisma;
    }


}