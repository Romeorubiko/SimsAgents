package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class UsarVater extends Accion {
    private Energia energia;
    private Vejiga vejiga;

    public UsarVater() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Vejiga getVejiga() {
        return vejiga;
    }

    public void setVejiga(Vejiga vejiga) {
        this.vejiga = vejiga;
    }

}
