package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;
import ontologia.conceptos.*;

public class TVEstropeada extends Predicado {

    private CanalTV canal;
    private Energia energia;
    private Diversion diversion;

    public TVEstropeada() {
    }

    public TVEstropeada(CanalTV canal, Energia energia, Diversion diversion) {
        this.canal = canal;
        this.energia = energia;
        this.diversion = diversion;
    }

    public CanalTV getCanalTV() {
        return canal;
    }

    public void setCanalTV(CanalTV c) {
        canal = c;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion d) {
        diversion = d;
    }

}