package ontologia.acciones;

import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

/**
 * Created by eldgb on 26-Oct-17.
 */
public class EntrenarMaquinaEjercicios {

    private Energia energia;

    private Higiene higiene;

    private Deporte deporte;


    public EntrenarMaquinaEjercicios() {
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
}
