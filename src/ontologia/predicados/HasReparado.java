package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

public class HasReparado extends Predicado {

    private Energia energia;
    private Higiene higiene;
    private Mecanica mecanica;

    public HasReparado() {
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene e) {
        higiene = e;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public Mecanica getMecanica() {
        return mecanica;
    }

    public void setMecanica(Mecanica m) {
        mecanica = m;
    }
}
