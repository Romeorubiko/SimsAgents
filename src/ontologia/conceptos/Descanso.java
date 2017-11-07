package ontologia.conceptos;
import ontologia.Concepto;

public class Descanso extends Concepto {

    public enum tipoDescanso {
        DORMIR, RELAJARSE
    }
    private tipoDescanso tipo;

    public Descanso() {

    }



     public tipoDescanso getTipoDescanso() {

     return tipo;

     }



     public void setTipoDescanso (tipoDescanso tipo) {

     this.tipo = tipo;

     }
}
