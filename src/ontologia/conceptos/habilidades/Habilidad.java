package ontologia.conceptos.habilidades;

import ontologia.Concepto;

public abstract class Habilidad extends Concepto {
    private int nivel;
    private int experiencia;
    public static final int HB_POCO = 10;
    public static final int HB_NORMAL = 20;
    public static final int HB_MUCHO = 40;

    public Habilidad() {
        this.nivel = 0;
        this.experiencia = 0;
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        if (nivel==0) {
            if (experiencia > 100) {
                this.experiencia = experiencia - 100;
                nivel++;
            } else {
                this.experiencia = experiencia;
            }
        }
        else if (nivel <= 10) {
            if (experiencia > 100 * (nivel+1)) {
                this.experiencia = experiencia - 100 * (nivel+1);
                nivel++;
            } else {
                this.experiencia = experiencia;
            }
        }
    }
}