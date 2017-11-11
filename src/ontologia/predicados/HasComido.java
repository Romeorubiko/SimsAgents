package ontologia.predicados;

import jadex.runtime.*;
import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class HasComido extends Predicado {
    
private Energia energia;
private Hambre hambre;

  public HasComido()
      {;}
  
  public HasComido(Energia energia, Hambre hambre)
  {
	  this.energia = energia;
      this.hambre = hambre;
  }

      public Energia getEnergia() {
           return energia;
       }
       public void setEnergia(Energia e) {
           energia=e;
       }
       public Hambre getHambre() {
           return hambre;
       }
       public void setHambre(Hambre c) {
          hambre=c;
       }
}
