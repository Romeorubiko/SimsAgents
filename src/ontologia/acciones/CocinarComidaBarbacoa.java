package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;
import ontologia.conceptos.habilidades.*;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class CocinarComidaBarbacoa extends Accion {

    private Higiene higiene;
    private Hambre hambre;
    private Diversion diversion;
    private Cocina cocina;

    public CocinarComidaBarbacoa() {
    }


	public CocinarComidaBarbacoa(Higiene higiene, Hambre hambre, Diversion diversion, Cocina cocina) {
		this.higiene = higiene;
		this.hambre = hambre;
		this.diversion = diversion;
		this.cocina = cocina;
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

    public void setDiversion(Diversion diversion) {
        this.diversion = diversion;
    }

    public Hambre getHambre() {
        return hambre;
    }

    public void setHambre(Hambre hambre) {
        this.hambre= hambre;
    }

    public Cocina getCocina() {
        return cocina;
    }

    public void setCocina(Cocina cocina) {
        this.cocina = cocina;
    }

}
