package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class HasDescansado extends Predicado {
	
	private Energia energia;

	public HasDescansado()
    {;}
	
		
	public Energia getEnergia() {
        return energia;
    }
    public void setEnergia(Energia e) {
        energia=e;
    }

}
