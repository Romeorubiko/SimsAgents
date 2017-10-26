package ontologia.acciones;

import jadex.runtime.*;
import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class JugarPartidaConsola extends Accion {
	private Energia energia;
	private Diversion diversion;
	private Higiene higiene;
	private InteraccionSocial interaccionsocial;

	 public JugarPartidaConsola()
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
       public void setHigiene(Higiene d) {
           higiene=d;
       }

        public InteraccionSocial getInteraccionSocial() {
           return interaccionsocial;
       }
       public void setInteraccionSocial(InteraccionSocial i) {
           interaccionsocial=i;
       }
}
