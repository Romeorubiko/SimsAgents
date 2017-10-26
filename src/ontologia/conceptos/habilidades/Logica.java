package ontologia.conceptos.habilidades;

import jadex.runtime.*;
import ontologia.Concepto; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class Logica extends Habilidad{
    private int nivel;
    private int experiencia;

    public Logica() {
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }
}
