package ontologia.acciones;

import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class Escribir extends Accion {
    private Energia energia;
    private Diversion diversion;
    private Escritura escritura;

    public Escribir() {
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

    public Escritura getEscritura() {
        return escritura;
    }

    public void setEscritura(Escritura escritura) {
        this.escritura = escritura;
    }
}
