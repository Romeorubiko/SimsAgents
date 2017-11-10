package ontologia.predicados;

import ontologia.Predicado; 
import ontologia.conceptos.habilidades.*; 
import ontologia.conceptos.necesidades.*;

public class LlamadaFallida extends Predicado {
    
	private Energia energia;
	private InteraccionSocial interaccion;
	private Carisma carisma;

	public LlamadaFallida()
    	{;}
	
	public LlamadaFallida(Energia energia, InteraccionSocial interaccion, Carisma carisma)
    	{
		this.energia=energia;
		this.interaccion=interaccion;
		this.carisma=carisma;
    	}

	public Energia getEnergia() {
    	    return energia;
    	}
    	public void setEnergia(Energia e) {
        	energia=e;
    	}
	
	public InteraccionSocial getInteraccionSocial() {
        	return interaccion;
    	}
    	public void setInteraccionSocial(InteraccionSocial i) {
        	interaccion=i;
    	}
	
	public Carisma getCarisma() {
        	return carisma;
    	}
    	public void setCarisma(Carisma c) {
        	carisma=c;
    	}
}
