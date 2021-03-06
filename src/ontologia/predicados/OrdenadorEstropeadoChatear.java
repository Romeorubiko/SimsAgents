package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class OrdenadorEstropeadoChatear extends Predicado {
    private Energia energia;
    private InteraccionSocial interaccionSocial;
    private Diversion diversion;

    public OrdenadorEstropeadoChatear() {
    }

    public OrdenadorEstropeadoChatear(Energia energia, InteraccionSocial interaccionSocial, Diversion diversion) {
        this.energia = energia;
        this.interaccionSocial = interaccionSocial;
        this.diversion = diversion;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public InteraccionSocial getInteraccionSocial() {
        return interaccionSocial;
    }

    public void setInteraccionSocial(InteraccionSocial interaccionSocial) {
        this.interaccionSocial = interaccionSocial;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion diversion) {
        this.diversion = diversion;
    }
}
