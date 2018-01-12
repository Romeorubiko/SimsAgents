package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class OrinarSuelo extends Accion {

    private Vejiga vejiga;
    public OrinarSuelo(Vejiga vejiga, Higiene higiene, Diversion diversion) {
		super();
		this.vejiga = vejiga;
		this.higiene = higiene;
		this.diversion = diversion;
	}

	private Higiene higiene;
    private Diversion diversion;

    public OrinarSuelo() {
    }

    public Vejiga getVejiga() {
        return vejiga;
    }

    public void setVejiga(Vejiga v) {
        vejiga = v;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion d) {
        diversion = d;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene h) {
        higiene = h;
    }

}
