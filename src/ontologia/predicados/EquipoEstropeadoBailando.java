package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;
public class EquipoEstropeadoBailando extends Predicado {
	
 	private Musica musica;
      
	public EquipoEstropeadoBailando()
    {;}
	
	public Musica getMusica() {
        return musica;
    }
    public void setMusica(Musica m) {
        musica=m;
    }
}