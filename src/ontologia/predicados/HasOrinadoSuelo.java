package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class HasOrinadoSuelo extends Predicado {
    
private Vejiga vejiga;

  private Higiene higiene;

  private Diversion diversion;



	 public HasOrinadoSuelo()

      {
      }



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
