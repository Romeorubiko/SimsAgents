package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;


public class RecogerFrutos extends Accion {
    private Energia energia;
    private Hambre hambre;
    private Jardineria jardineria;

    public RecogerFrutos() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Hambre getHambre() {
        return hambre;
    }

    public void setHambre(Hambre hambre) {
        this.hambre = hambre;
    }

    public Jardineria getJardineria() {
        return jardineria;
    }

    public void setJardineria(Jardineria jardineria) {
        this.jardineria = jardineria;
    }
}
