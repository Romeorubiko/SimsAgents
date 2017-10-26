package ontologia.conceptos.habilidades;

import jadex.runtime.*;


public class Guitarra extends Habilidad{
    private int nivel;
    private int experiencia;

    public Guitarra() {
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
