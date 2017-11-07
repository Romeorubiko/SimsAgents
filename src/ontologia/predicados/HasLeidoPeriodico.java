package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*;

public class HasLeidoPeriodico extends Predicado {
    
	private Energia energia;
	private Diversion diversion;
	private Logica logica;
	private Escritura escritura;
      
	public HasLeidoPeriodico()
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