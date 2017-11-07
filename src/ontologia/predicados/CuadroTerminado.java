package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*;

public class CuadroTerminado extends Predicado {
    private Energia energia;
    private Diversion diversion;
    private Pintura pintura;

    public CuadroTerminado() {
    }

    public CuadroTerminado(Energia energia, Diversion diversion, Pintura pintura) {
        this.energia = energia;
        this.diversion = diversion;
        this.pintura = pintura;
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

    public Pintura getPintura() {
        return pintura;
    }

    public void setPintura(Pintura pintura) {
        this.pintura = pintura;
    }
}
