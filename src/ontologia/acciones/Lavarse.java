package ontologia.acciones;
import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class Lavarse extends Accion {
    private Energia energia;
    private Higiene higiene; 

    public Lavarse() {
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
}
