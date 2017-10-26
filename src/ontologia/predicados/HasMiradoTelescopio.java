package ontologia.predicados;

import jadex.runtime.*;
import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class HasMiradoTelescopio extends Predicado {
	private InteraccionSocial interaccionSocial;
    private Diversion diversion;
    private Logica logica;

    public HasMiradoTelescopio() {
    }

    public InteraccionSocial getInteraccionSocial() {
        return interaccionSocial;
    }

    public void setInteraccionSocial(InteraccionSocial interaccionSocial) {
        this.interaccionSocial = interaccionSocial;
    }

   public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion diversion) {
        this.diversion = diversion;
    }

   public Logica getLogica() {
        return logica;
    }

    public void setLogica(Logica logica) {
        this.logica = logica;
    }
}