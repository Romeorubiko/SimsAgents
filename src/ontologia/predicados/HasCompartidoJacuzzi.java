package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class HasCompartidoJacuzzi extends Predicado {
    private Energia energia;
    private Higiene higiene;
    private InteraccionSocial interaccionSocial;


    public HasCompartidoJacuzzi() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene higiene) {
        this.higiene = higiene;
    }

    public InteraccionSocial getInteraccionSocial() {
        return interaccionSocial;
    }

    public void setInteraccionSocial(InteraccionSocial interaccionSocial) {
        this.interaccionSocial = interaccionSocial;
    }

}
