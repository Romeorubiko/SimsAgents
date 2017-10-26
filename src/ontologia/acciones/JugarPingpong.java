package ontologia.acciones;

import jadex.runtime.*;
import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class JugarPingpong extends Accion {
  
	private Energia energia;
	private Diversion diversion;
	private InteraccionSocial interaccionsocial;
  private Higiene higiene;
  private Deporte deporte;

	 public JugarPingpong()
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

        public Higiene getHigiene() {
           return higiene;
       }
       public void setHigiene(Higiene h) {
           higiene=h;
       }

       public Deporte getDeporte() {
           return deporte;
       }
       public void setDeporte(Deporte s) {
           deporte=s;
       }
}
