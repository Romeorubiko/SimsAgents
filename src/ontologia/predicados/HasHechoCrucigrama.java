package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*;

public class HasHechoCrucigrama extends Predicado {
    
	private Energia energia;
	private Diversion diversion;
	private Logica logica;
      
	public HasHechoCrucigrama()
    {
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
	
	public Logica getLogica() {
        return logica;
    }
    public void setLogica(Logica l) {
        logica=l;
    }
}