package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class CompartirJacuzzi extends Accion {
    private Energia energia;
    private InteraccionSocial interaccion;
    private Higiene higiene;

    public CompartirJacuzzi() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public InteraccionSocial getInteraccionSocial() {
        return interaccion;
    }

    public void setInteraccionSocial(InteraccionSocial interaccion) {
        this.interaccion = interaccion;
    }

    public Higiene getHigiene() {
        return higiene;
    }


    public void setHigiene(Higiene higiene) {
        this.higiene = higiene;

    }
}
