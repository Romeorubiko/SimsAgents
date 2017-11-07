package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*;

public class NoHasEntrenado extends Predicado {
    
private Energia energia;
private Higiene higiene;
private Hambre hambre;
private Deporte deporte;

      public NoHasEntrenado()
      {
      }

      public Energia getEnergia() {
           return energia;
       }
       public void setEnergia(Energia e) {
           energia=e;
       }

        public Higiene getHigiene() {
           return higiene;
       }
       public void setHigiene(Higiene h) {
           higiene=h;
       }
       
        public Hambre getHambre() {
           return hambre;
       }
       public void setHambre(Hambre ha) {
           hambre=ha;
       }      

       public Deporte getDeporte() {
           return deporte;
       }
       public void setDeporte(Deporte s) {
           deporte=s;
       }

}
