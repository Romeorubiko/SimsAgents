package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

public class LlamarSim extends Accion {

    private InteraccionSocial interaccion;
    private Energia energia;
    private Carisma carisma;

    public LlamarSim() {
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

    public Carisma getCarisma() {
        return carisma;
    }

    public void setCarisma(Carisma c) {
        carisma = c;
    }
}