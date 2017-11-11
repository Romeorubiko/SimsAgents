package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

public class ContarChiste extends Accion {

    private InteraccionSocial interaccion;
    private Energia energia;
    private Diversion diversion;
    private Carisma carisma;
    private Higiene higiene;

    public ContarChiste() {
    }

    public InteraccionSocial getInteraccionSocial() {
        return interaccion;
    }

    public void setInteraccionSocial(InteraccionSocial i) {
        interaccion = i;
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

    public Carisma getCarisma() {
        return carisma;
    }

    public void setCarisma(Carisma c) {
        carisma = c;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene h) {
        higiene = h;
    }
}
