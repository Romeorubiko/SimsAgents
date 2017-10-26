package ontologia.acciones;

import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class EscucharMusica extends Accion {
	
      /* El sim escucha el equipo de música sin importarle el tipo de música que
	  *  esté sonando ya que todos aumentan por igual la diversión del Sim.
	  */
	  
       private Diversion diversion;
	   private Energia energia;

	   public EscucharMusica()
       {;}
	   
       public Diversion getDiversion() {
           return diversion;
       }
       public void setDiversion(Diversion d) {
           diversion=d;
       }
	   
	   public Energia getEnergia() {
           return energia;
       }
       public void setEnergia(Energia e) {
           energia=e;
       }
}