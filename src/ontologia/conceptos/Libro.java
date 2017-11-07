package ontologia.conceptos;

import ontologia.Concepto;

public class Libro extends Concepto {
    public enum GenerosLiterarios {
        CARISMA, COCINA, JARDINERIA, PINTURA, FOTOGRAFIA, LOGICA, CIENCIA,
        NOVELA, NEGOCIOS, PERIODISMO, CRIMINAL, MEDICINA, MILITAR, DERECHO, DEPORTES, MUSICA, POLITICA
    }

    private GenerosLiterarios genero;
    private int tiempoLectura;

    public Libro() {
        tiempoLectura = 4500;
    }

    public GenerosLiterarios getGenero() {
        return genero;
    }

    public void setGenero(GenerosLiterarios genero) {
        this.genero = genero;
    }

    public int getTiempoLectura() {
        return tiempoLectura;
    }

    public void setTiempoLectura(int tiempoLectura) {
        this.tiempoLectura = tiempoLectura;
    }
}
