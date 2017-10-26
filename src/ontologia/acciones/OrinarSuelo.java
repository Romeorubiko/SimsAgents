package ontologia.acciones;

import jadex.runtime.*;
import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class OrinarSuelo extends Accion {
  
	private Vejiga vejiga;
  private Higiene higiene;
  private Diversion diversion;

	 public OrinarSuelo()
      {;}

      public Vejiga getVejiga() {
           return vejiga;
       }
       public void setVejiga(Vejiga v) {
           vejiga=v;
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
