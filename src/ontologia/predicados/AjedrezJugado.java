package ontologia.predicados;

import jadex.runtime.*;
import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class AjedrezJugado extends Predicado {
    
private Energia energia;
private Diversion diversion;
private InteraccionSocial interaccionsocial;
private Logica logica;

      public AjedrezJugado()
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

        public InteraccionSocial getInteraccionSocial() {
           return interaccionsocial;
       }
       public void setInteraccionSocial(InteraccionSocial i) {
           interaccionsocial=i;
       }

       public Logica getLogica() {
           return logica;
       }
       public void setLogica(Logica l) {
           logica=l;
       }

}
