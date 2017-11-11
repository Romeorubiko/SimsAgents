package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

public class CocinarComida extends Accion {

    private Hambre hambre;
    private Energia energia;
    private Cocina cocina;

    public CocinarComida() {
    }

    public Hambre getHambre() {
        return hambre;
    }

    public void setHambre(Hambre h) {
        hambre = h;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public Cocina getCocina() {
        return cocina;
    }

    public void setCocina(Cocina c) {
        cocina = c;
    }
}
