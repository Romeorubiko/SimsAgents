package ontologia.predicados;


import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;


public class VaterEstropeado extends Predicado {
    private Vejiga vejiga;

    public VaterEstropeado() {
    }


    public VaterEstropeado(Vejiga vejiga) {
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
