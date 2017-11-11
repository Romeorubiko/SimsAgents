package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;
import ontologia.conceptos.*;

public class VerTV extends Accion {

      /* El sim puede ver el canal de TV que desee. Todos los canales aumentaran
	  *  su diversión y disminuirán su energía, sin embargo, sólo algunos le proporcionan
	  *  la habilidad de carisma y otro la de cocina. Las modificaciones de las habilidades se
	  *  harán a través de la clase Canal, ya que dependiendo del tipo de canal se modificará una habilidad u otra.
	  */

    private CanalTV que;
    private Diversion diversion;
    private Energia energia;

    public VerTV() {
    }

    public CanalTV getCanalTV() {
        return que;
    }

    public void setCanalTV(CanalTV c) {
        que = c;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion d) {
        diversion = d;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia e) {
        energia = e;
    }
}