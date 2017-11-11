package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class LibroLeido extends Predicado {
    private Energia energia;
    private Diversion diversion;
    private Habilidad habilidad; //Esta habilidad depende del genero leído, no se puede preasignar.

    public LibroLeido() {
    }

    public LibroLeido(Energia energia, Diversion diversion, Habilidad habilidad) {
        this.energia = energia;
        this.diversion = diversion;
        this.habilidad = habilidad;
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

    public Habilidad getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(Habilidad habilidad) {
        this.habilidad = habilidad;
    }
}
