package ontologia.conceptos;
import ontologia.Concepto; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

import jadex.runtime.*;

public class Descanso extends Concepto {

    public enum tipoDescanso {
        DORMIR, RELAJARSE
    }
    private tipoDescanso tipo;

    public Descanso() {

    ;

    }



     public tipoDescanso getTipoDescanso() {

     return tipo;

     }



     public void setTipoDescanso (tipoDescanso tipo) {

     this.tipo = tipo;

     }
}
