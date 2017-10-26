package ontologia.acciones;

import jadex.runtime.*;
import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
public class ComerComida extends Accion {
    

private Energia energia;
private Hambre hambre;

	  public ComerComida()
      {;}

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
