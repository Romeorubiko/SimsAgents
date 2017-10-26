package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class TVEstropeadaEjercicio extends Predicado {
    
	private Energia energia;
	private Diversion diversion;
	private Hambre hambre;
	private Higiene higiene;
	
	private Deporte fisico;
      
	public TVEstropeadaEjercicio()
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
    
	public Hambre getHambre() {
        return hambre;
    }
    public void setHambre(Hambre h) {
        hambre=h;
    }
    
	public Higiene getHigiene() {
        return higiene;
    }
    public void setHigiene(Higiene h) {
        higiene=h;
    }

    public Deporte getDeporte() {
        return fisico;
    }
    public void setDeporte(Deporte f) {
        fisico=f;
    }
}