package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class VaterUsado extends Predicado {

    private Vejiga vejiga;

    public VaterUsado() {
    }


    public Vejiga getVejiga() {
        return vejiga;
    }

    public void setVejiga(Vejiga vejiga) {
        this.vejiga = vejiga;
    }


	public VaterUsado(Vejiga vejiga) {
		super();
		this.vejiga = vejiga;
	}


}