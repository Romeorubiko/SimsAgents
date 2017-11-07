package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class GastarBromaTel extends Accion {
    
	   private Diversion diversion;
	   private Energia energia;

	    public GastarBromaTel()
       {
       }
	   
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