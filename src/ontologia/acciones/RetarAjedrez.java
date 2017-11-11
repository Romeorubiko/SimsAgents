package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class RetarAjedrez extends Accion {

    private Energia energia;
    private InteraccionSocial interaccionsocial;


    public RetarAjedrez() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public InteraccionSocial getInteraccionSocial() {
        return interaccionsocial;
    }

    public void setInteraccionSocial(InteraccionSocial i) {
        interaccionsocial = i;
    }
}