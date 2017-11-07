package ontologia.conceptos;

import ontologia.Concepto;
import ontologia.conceptos.habilidades.*;

public class CanalTV extends Concepto {

    private enum Canales {COCINA, PELICULAS, DEPORTES, SERIES}

    private Canales canal;
    private Cocina cocina;
    private Carisma carisma;

    public CanalTV() {
    }

    public Canales getCanal() {
        return canal;
    }

    public void setCanal(Canales canal) {
        this.canal = canal;
    }

    public Cocina getCocina() {
        return cocina;
    }

    public void setCocina(Cocina c) {
        cocina = c;
    }

    public Carisma getCarisma() {
        return carisma;
    }

    public void setCarisma(Carisma c) {
        carisma = c;
    }
}