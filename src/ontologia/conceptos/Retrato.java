package ontologia.conceptos;

import ontologia.Concepto; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;
import jadex.runtime.*;

public class Retrato extends Concepto {
    private Foto.FotoSize fotoSize;
    private Foto.Filtro filtro;

    public Retrato() {
    }

    public Foto.FotoSize getFotoSize() {
        return fotoSize;
    }

    public void setFotoSize(Foto.FotoSize fotoSize) {
        this.fotoSize = fotoSize;
    }

    public Foto.Filtro getFiltro() {
        return filtro;
    }

    public void setFiltro(Foto.Filtro filtro) {
        this.filtro = filtro;
    }
}
