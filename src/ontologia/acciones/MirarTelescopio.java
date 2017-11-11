package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;


public class MirarTelescopio extends Accion {
    private Diversion diversion;
    private InteraccionSocial interaccionSocial;
    private Logica logica;

    public MirarTelescopio() {
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion diversion) {
        this.diversion = diversion;
    }

    public InteraccionSocial getInteraccionSocial() {
        return interaccionSocial;
    }

    public void setInteraccionSocial(InteraccionSocial interaccionSocial) {
        this.interaccionSocial = interaccionSocial;
    }

    public Logica getLogica() {
        return logica;
    }

    public void setLogica(Logica logica) {
        this.logica = logica;
    }
}
