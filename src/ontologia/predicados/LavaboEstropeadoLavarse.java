package ontologia.predicados;

import jadex.runtime.*;
import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;



public class LavaboEstropeadoLavarse extends Predicado {
    
 private Energia energia;
 private Higiene higiene;


  public LavaboEstropeadoLavarse()
      {;}
    
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
