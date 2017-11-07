package ontologia.acciones;

import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*;

public class HacerCrucigrama extends Accion {

    private Diversion diversion;
	private Energia energia;
	private Logica logica;
	   
	public  HacerCrucigrama()
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
    
	public Logica getLogica() {
        return logica;
    }
    public void setLogica(Logica l) {
        logica=l;
    }
}