package ontologia.acciones;

import jadex.runtime.*;
import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;


public class CuidarPlantas extends Accion {
    private Energia energia;
    private Higiene higiene;
    private Jardineria jardineria;

    public CuidarPlantas() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

     public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene higiene) {
        this.higiene= higiene;
    }

    public Jardineria getJardineria() {
        return jardineria;
    }

    public void setJardineria(Jardineria jardineria) {
        this.jardineria = jardineria;
    }
}
