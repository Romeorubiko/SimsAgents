package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;

public class HacerLaCama extends Accion {
    private Higiene higiene;

    public HacerLaCama() {
    }



    public HacerLaCama(Higiene higiene) {
		super();
		this.higiene = higiene;
	}


	public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene higiene) {
        this.higiene = higiene;
    }
}
