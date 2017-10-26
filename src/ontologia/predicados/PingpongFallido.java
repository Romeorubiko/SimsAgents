package ontologia.predicados;

import jadex.runtime.*;
import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class PingpongFallido extends Predicado {
    
private Energia energia;
private Higiene higiene;
private InteraccionSocial interaccionsocial;
private Diversion diversion;
private Deporte deporte;

      public PingpongFallido()
      {;}

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
       public InteraccionSocial getInteraccionSocial() {
           return interaccionsocial;
       }
       public void setInteraccionSocial(InteraccionSocial i) {
           interaccionsocial=i;
       }

       public Diversion getDiversion() {
           return diversion;
       }
       public void setDiversion(Diversion d) {
           diversion=d;
       }


       public Deporte getDeporte() {
           return deporte;
       }
       public void setDeporte(Deporte s) {
           deporte=s;
       }

}
