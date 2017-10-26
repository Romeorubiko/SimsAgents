package ontologia.acciones;

import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class UsarEspejo extends Accion {
    private Energia energia;
    private Carisma carisma;

    public UsarEspejo() {
    }
     public Carisma getCarisma() {
        return carisma;
    }

    public void setCarisma(Carisma c) {
        carisma = c;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }
}
