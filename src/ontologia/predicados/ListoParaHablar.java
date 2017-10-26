package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class ListoParaHablar extends Predicado {
    
	private Energia energia;
	private InteraccionSocial interaccion;
	private Carisma carisma;
      
	public ListoParaHablar()
    {;}
	
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