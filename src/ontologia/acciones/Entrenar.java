package ontologia.acciones;

import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

/**
 * Created by eldgb on 26-Oct-17.
 */
public class Entrenar {

    private Energia energia;

    private Higiene higiene;

    private Deporte deporte;

    private Hambre hambre;

    public Entrenar() {
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene h) {
        higiene = h;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte s) {
        deporte = s;
    }

    public Hambre getHambre() {
        return hambre;
    }

    public void setHambre(Hambre h) {
        hambre = h;
    }
}
