package ontologia.acciones;

import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class SacarFotoRetrato extends Accion {
    private Retrato retrato;
    private InteraccionSocial interaccionSocial;
    private Diversion diversion;
    private Fotografia fotografia;

    public SacarFotoRetrato() {
    }

    public Retrato getRetrato() {
        return retrato;
    }

    public void setRetrato(Retrato retrato) {
        this.retrato = retrato;
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

    public Fotografia getFotografia() {
        return fotografia;
    }

    public void setFotografia(Fotografia fotografia) {
        this.fotografia = fotografia;
    }
}
