package Suelo;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.*;
import java.util.*;
import jadex.runtime.*;
import jadex.adapter.fipa.*;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;


/**
 * Created by eldgb on 25-Oct-17.
 */
public class DormirSueloPlan extends Plan {

    public void body(){
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        DormirSuelo content = (DormirSuelo)peticion.getContent();

        Higiene h = content.getHigiene();
        h.setGrado(content.getHigiene().getGrado() - Necesidad.NC_MUCHO);
        content.setHigiene(h);

        Diversion d = content.getDiversion();
        d.setGrado(content.getDiversion().getGrado() - Necesidad.NC_NORMAL);
        content.setDiversion(d);

        Energia e = content.getEnergia();
        e.setGrado(content.getEnergia().getGrado() + Necesidad.NC_NORMAL);
        content.setEnergia(e);


        try {
            wait(Accion.TIEMPO_LARGO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        IMessageEvent respuesta = createMessageEvent("has_dormido_suelo");
        respuesta.setContent(content);



    }
}
