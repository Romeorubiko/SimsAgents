package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class AparatoEstropeadoJugar extends Predicado {
    private Energia energia;
    private Diversion diversion;
    private Logica logica;

    public AparatoEstropeadoJugar() {
    }

    public AparatoEstropeadoJugar(Energia energia, Diversion diversion, Logica logica) {
        this.energia = energia;
        this.diversion = diversion;
        this.logica = logica;
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

    public Logica getLogica() {
        return logica;
    }

    public void setLogica(Logica logica) {
        this.logica = logica;
    }
}
