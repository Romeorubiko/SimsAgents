package ontologia.conceptos;

import ontologia.Concepto; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class Libro extends Concepto {
    public enum GenerosLiterarios {
        CARISMA, COCINA, JARDINERIA, PINTURA, FOTOGRAFIA, LOGICA, CIENCIA,
        NOVELA, NEGOCIOS, PERIODISMO, CRIMINAL, MEDICINA, MILITAR, DERECHO, DEPORTES, MUSICA, POLITICA
    }

    private GenerosLiterarios genero;

    public Libro() {
    }

    public GenerosLiterarios getGenero() {
        return genero;
    }

    public void setGenero(GenerosLiterarios genero) {
        this.genero = genero;
    }
}
