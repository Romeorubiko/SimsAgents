package ontologia.predicados;

import jadex.runtime.*;
import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class NeveraEstropeadaBeber extends Predicado {
    private Energia energia;
    private Vejiga vejiga;
    private Diversion diversion;

    public NeveraEstropeadaBeber()
      {;}
    
    public NeveraEstropeadaBeber(Energia energia, Vejiga vejiga, Diversion diversion)
    {
    	this.energia = energia;
        this.vejiga = vejiga;
        this.diversion = diversion;
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
