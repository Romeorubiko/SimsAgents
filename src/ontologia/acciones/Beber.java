package ontologia.acciones;

import jadex.runtime.*;
import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;


public class Beber extends Accion {
    
private Bebida tipo;
private Energia energia;
private Vejiga vejiga;
private Diversion diversion;
       
	   public Beber()
      {;}

       public Bebida getTipo() {
           return tipo;
       }
       public void setTipo(Bebida c) {
           tipo=c;
       }

       public Energia getEnergia() {
           return energia;
       }
       public void setEnergia(Energia e) {
           energia=e;
       }

       public Vejiga getVejiga() {
           return vejiga;
       }
       public void setVejiga(Vejiga c) {
           vejiga=c;
       }
	   	   public Diversion getDiversion() {
           return diversion;
       }
       public void setDiversion(Diversion d) {
           diversion=d;
       }
}