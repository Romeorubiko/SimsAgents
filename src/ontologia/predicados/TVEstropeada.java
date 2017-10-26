package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class TVEstropeada extends Predicado {
    
	private CanalTV canal;
	private Energia energia;
	private Diversion diversion;
      
	public TVEstropeada()
    {;}
	
	public CanalTV getCanalTV() {
        return canal;
    }
    public void setCanalTV(CanalTV c) {
        canal=c;
    }
	   
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
    
}