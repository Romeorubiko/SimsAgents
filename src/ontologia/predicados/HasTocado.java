package ontologia.predicados;

import jadex.runtime.*;
import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class HasTocado extends Predicado {
    
private Energia energia;
private Diversion diversion;
private Guitarra guitarra;

      public HasTocado()
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

        public Guitarra getGuitarra() {
           return guitarra;
       }
       public void setGuitarra(Guitarra g) {
           guitarra=g;
       }

}
