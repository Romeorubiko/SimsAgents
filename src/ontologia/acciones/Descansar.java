package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;
import ontologia.conceptos.*;

public class Descansar extends Accion {
    private Descanso tipo;
    private Energia energia;

    public Descansar() {
    }


    public Descanso getTipo() {
        return tipo;
    }

    public void setTipo(Descanso c) {
        tipo = c;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }


}
