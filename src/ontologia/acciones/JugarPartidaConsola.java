package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class JugarPartidaConsola extends Accion {
    private Energia energia;
    private Diversion diversion;
    private Higiene higiene;
    private InteraccionSocial interaccionsocial;

    public JugarPartidaConsola() {
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

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene d) {
        higiene = d;
    }

    public InteraccionSocial getInteraccionSocial() {
        return interaccionsocial;
    }

    public void setInteraccionSocial(InteraccionSocial i) {
        interaccionsocial = i;
    }
}
