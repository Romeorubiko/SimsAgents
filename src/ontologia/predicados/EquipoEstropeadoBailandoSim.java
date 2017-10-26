package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class EquipoEstropeadoBailandoSim extends Predicado {
     
	private Musica musica;
	private InteraccionSocial interaccion;
      
	public EquipoEstropeadoBailandoSim()
    {;}
	
	public Musica getMusica() {
        return musica;
    }
    public void setMusica(Musica m) {
        musica=m;
    }
	
	public InteraccionSocial getInteraccionSocial() {
        return interaccion;
    }
    public void setInteraccionSocial(InteraccionSocial i) {
        interaccion=i;
    }
}