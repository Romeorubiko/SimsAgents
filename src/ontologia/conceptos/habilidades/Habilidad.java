package ontologia.conceptos.habilidades;

import ontologia.Concepto;

public abstract class Habilidad extends Concepto {
    private int nivel;
    private int experiencia;
    public static final int HB_POCO = 10;
    public static final int HB_NORMAL = 20;
    public static final int HB_MUCHO = 40;

    public Habilidad() {
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        if (nivel <= 10) {
            if (experiencia > 100 * nivel) {
                this.experiencia = experiencia - 100 * nivel;
                nivel++;
            } else {
                this.experiencia = experiencia;
            }
        }
    }
}