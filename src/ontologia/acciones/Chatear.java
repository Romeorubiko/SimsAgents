package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class Chatear extends Accion {
    private Energia energia;
    private InteraccionSocial interaccionSocial;
    private Diversion diversion;

    public Chatear() {
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
