package ontologia.conceptos;

import ontologia.Concepto;

public class Bebida extends Concepto {
    public enum tipoBebida {
        AGUA, CAFE, REFRESCO, ALCOHOL
    }

    private tipoBebida tipo;

    public Bebida() {
    }

    public tipoBebida getTipoBebida() {
        return tipo;
    }

    public void setTipoBebida(tipoBebida tipo) {
        this.tipo = tipo;
    }

}
