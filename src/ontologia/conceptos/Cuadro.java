package ontologia.conceptos;

import ontologia.Concepto;

public class Cuadro extends Concepto {
    public enum Arte {
        POP, REALISTA, ABSTRACTO, CLASICO, SURREALISTA, IMPRESIONISTA
    }

    private Arte arte;
    private int tiempoPintado;

    public Cuadro() {
        tiempoPintado = 10500;
    }

    public Arte getArte() {
        return arte;
    }

    public void setArte(Arte arte) {
        this.arte = arte;
    }

    public int getTiempoPintado() {
        return tiempoPintado;
    }

    public void setTiempoPintado(int tiempoPintado) {
        this.tiempoPintado = tiempoPintado;
    }
}
