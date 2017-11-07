package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

public class EjercicioTV extends Accion {

    private Diversion diversion;
    private Energia energia;
    private Hambre hambre;
    private Higiene higiene;
    private Deporte fisico;

    public EjercicioTV() {
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion d) {
        diversion = d;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public Hambre getHambre() {
        return hambre;
    }

    public void setHambre(Hambre h) {
        hambre = h;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene h) {
        higiene = h;
    }

    public Deporte getDeporte() {
        return fisico;
    }

    public void setDeporte(Deporte f) {
        fisico = f;
    }
}