package ontologia.predicados;

import ontologia.conceptos.habilidades.Escritura;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.habilidades.Logica;

public class HasLeidoPeriodico {
    private Diversion diversion;
    private Energia energia;
    private Logica logica;
    private Escritura escritura;

    public HasLeidoPeriodico() {
    }

    public HasLeidoPeriodico(Diversion diversion, Energia energia, Logica logica, Escritura escritura) {
        this.diversion = diversion;
        this.energia = energia;
        this.logica = logica;
        this.escritura = escritura;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion diversion) {
        this.diversion = diversion;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Logica getLogica() {
        return logica;
    }

    public void setLogica(Logica logica) {
        this.logica = logica;
    }

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }
}
