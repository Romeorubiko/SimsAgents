package ontologia.acciones;

import jadex.runtime.*;
import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class DormirSuelo extends Accion {
  
	private Energia energia;
  private Higiene higiene;
  private Diversion diversion;

	 public DormirSuelo()
      {;}

      public Energia getEnergia() {
           return energia;
       }
       public void setEnergia(Energia e) {
           energia=e;
       }

       public Diversion getDiversion() {
           return diversion;
       }
       public void setDiversion(Diversion d) {
           diversion=d;
       }

        public Higiene getHigiene() {
           return higiene;
       }
       public void setHigiene(Higiene h) {
           higiene=h;
       }

}
