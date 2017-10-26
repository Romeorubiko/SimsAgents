package ontologia.conceptos;

import ontologia.Concepto; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class Juego extends Concepto {
    public enum TipoJuego {VIDEOJUEGO, AJEDREZ}

    private TipoJuego tipo;

    public Juego() {
    }

    public TipoJuego getTipo() {
        return tipo;
    }

    public void setTipo(TipoJuego tipo) {
        this.tipo = tipo;
    }
}
