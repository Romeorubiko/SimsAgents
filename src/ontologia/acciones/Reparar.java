
package ontologia.acciones;

import jadex.runtime.*;
import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;


public class Reparar extends Accion {
  
    private Higiene higiene;
    private Energia energia;
    private Mecanica mecanica;

    public Reparar() {
      ;
    }



    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene higiene) {
        this.higiene = higiene;
    }

     public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia= energia;
    }

    public Mecanica getMecanica() {
        return mecanica;
    }

    public void setMecanica(Mecanica mecanica) {
        this.mecanica = mecanica;
    }
}
