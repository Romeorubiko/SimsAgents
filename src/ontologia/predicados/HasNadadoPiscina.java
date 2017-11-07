package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

public class HasNadadoPiscina extends Predicado {
    private Energia energia;
    private Higiene higiene;
    private Diversion diversion;
    private Deporte deporte;

    public HasNadadoPiscina() {
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

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversión(Diversion diversion) {
        this.diversion = diversion;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }
}
