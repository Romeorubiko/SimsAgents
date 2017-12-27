package ontologia.acciones;

import ontologia.conceptos.habilidades.Mecanica;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;

public class RepararTv {
    private Higiene higiene;
    private Energia energia;
    private Mecanica mecanica;

    public RepararTv() {
    }


    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene higiene) {
        this.higiene = higiene;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Mecanica getMecanica() {
        return mecanica;
    }

    public void setMecanica(Mecanica mecanica) {
        this.mecanica = mecanica;
    }
}
