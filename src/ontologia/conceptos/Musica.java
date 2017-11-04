package ontologia.conceptos;

import ontologia.Concepto;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;
import ontologia.conceptos.*;
import jadex.runtime.*;

public class Musica extends Concepto {

	private enum TiposMusica {
		ROCK, POP, CLASICA
	}

	public TiposMusica tipo;

	public Musica() {
		;
	}

	public TiposMusica getTipo() {
		return tipo;
	}

	public void setTipo(TiposMusica tipo) {
		this.tipo = tipo;
	}
}
