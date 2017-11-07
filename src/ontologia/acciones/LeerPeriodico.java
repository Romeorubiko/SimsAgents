package ontologia.acciones;

import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*;

public class LeerPeriodico extends Accion {

    private Diversion diversion;
	private Energia energia;
	private Logica logica;
	private Escritura escritura;
	   
	public LeerPeriodico()
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
    
	public Escritura getEscritura() {
        return escritura;
    }
    public void setEscritura(Escritura e) {
        escritura=e;
    }
    
	public Logica getLogica() {
        return logica;
    }
    public void setLogica(Logica l) {
        logica=l;
    }
}