package ontologia.conceptos;

import ontologia.Concepto;

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
