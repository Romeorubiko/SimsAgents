package ontologia.conceptos;

import ontologia.Concepto; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class Cuadro extends Concepto {
    public enum Arte {
        POP, REALISTA, ABSTRACTO, CLASICO, SURREALISTA, IMPRESIONISTA
    }

    private Arte arte;

    public Cuadro() {
    }

    public Arte getArte() {
        return arte;
    }

    public void setArte(Arte arte) {
        this.arte = arte;
    }
}
