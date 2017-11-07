package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.*;

public class EquipoEstropeadoBailando extends Predicado {
	
 	private Musica musica;
      
	public EquipoEstropeadoBailando()
    {
    }
	
	public Musica getMusica() {
        return musica;
    }
    public void setMusica(Musica m) {
        musica=m;
    }
}