package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.habilidades.Mecanica;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;

public class OrdenadorReparado extends Predicado {
    private Energia energia;
    private Higiene higiene;
    private Mecanica mecanica;

    public OrdenadorReparado() {
    }

    public OrdenadorReparado(Energia energia, Higiene higiene, Mecanica mecanica) {
        this.energia = energia;
        this.higiene = higiene;
        this.mecanica = mecanica;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene higiene) {
        this.higiene = higiene;
    }

    public Mecanica getMecanica() {
        return mecanica;
    }

    public void setMecanica(Mecanica mecanica) {
        this.mecanica = mecanica;
    }
}
