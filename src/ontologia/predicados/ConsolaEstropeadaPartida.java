package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;


public class ConsolaEstropeadaPartida extends Predicado {

    private Energia energia;
    private Diversion diversion;
    private InteraccionSocial interaccion;

    public ConsolaEstropeadaPartida() {

    }

    public ConsolaEstropeadaPartida(Energia energia, Diversion diversion, InteraccionSocial interaccionsocial) {
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

    public InteraccionSocial getInteraccionSocial() {
        return interaccion;
    }

    public void setInteraccionSocial(InteraccionSocial interaccion) {
        this.interaccion = interaccion;

    }


}
