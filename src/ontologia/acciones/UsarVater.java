package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class UsarVater extends Accion {

    private Vejiga vejiga;

    public UsarVater() {
    }

    public UsarVater(Vejiga vejiga) {
		super();
	
		this.vejiga = vejiga;
	}

    public Vejiga getVejiga() {
        return vejiga;
    }

    public void setVejiga(Vejiga vejiga) {
        this.vejiga = vejiga;
    }

}
