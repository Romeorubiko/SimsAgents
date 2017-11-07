package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class HasDormidoSuelo extends Predicado {
    
private Energia energia;

  private Higiene higiene;

  private Diversion diversion;



	 public HasDormidoSuelo()

      {
      }



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
