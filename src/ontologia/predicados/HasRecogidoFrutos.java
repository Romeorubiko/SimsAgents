package ontologia.predicados;

import jadex.runtime.*;
import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class HasRecogidoFrutos extends Predicado {
    private Energia energia;
    private Hambre hambre;
    private Jardineria jardineria;

    public HasRecogidoFrutos() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

   public Hambre getHambre() {
        return hambre;
    }

    public void setHambre(Hambre hambre) {
        this.hambre = hambre;
    }


    public Jardineria getJardineria() {
        return jardineria;
    }

    public void setJardineria(Jardineria jardineria) {
        this.jardineria = jardineria;
    }
}