package ontologia.conceptos;

import ontologia.Concepto;

public class Musica extends Concepto {

    public enum TiposMusica {
        ROCK, POP, CLASICA
    }

    private TiposMusica tipo;

    public Musica() {
    }

    public TiposMusica getTipo() {
        return tipo;
    }

    public void setTipo(TiposMusica tipo) {
        this.tipo = tipo;
    }
}
