package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*;

public class CamaraEstropeadaSacarFoto extends Predicado {
    private Energia energia;
    private Diversion diversion;
    private Fotografia fotografia;

    public CamaraEstropeadaSacarFoto() {
    }

    public CamaraEstropeadaSacarFoto(Energia energia, Diversion diversion, Fotografia fotografia) {
        this.energia = energia;
        this.diversion = diversion;
        this.fotografia = fotografia;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion diversion) {
        this.diversion = diversion;
    }

    public Fotografia getFotografia() {
        return fotografia;
    }

    public void setFotografia(Fotografia fotografia) {
        this.fotografia = fotografia;
    }
}
