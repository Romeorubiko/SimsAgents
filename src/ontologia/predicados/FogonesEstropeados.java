package ontologia.predicados;

import jadex.runtime.*;
import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;



public class FogonesEstropeados extends Predicado {
    
private Energia energia;
private Cocina cocina;
private Hambre hambre;

  public FogonesEstropeados()
      {;}

      public Energia getEnergia() {
           return energia;
       }
       public void setEnergia(Energia e) {
           energia=e;
       }
       public Cocina getCocina() {
           return cocina;
       }
       	public Hambre getHambre() {
           return hambre;
       }
       public void setHambre(Hambre h) {
           hambre=h;
       }
       public void setCocina(Cocina c) {
           cocina=c;
       }
}
