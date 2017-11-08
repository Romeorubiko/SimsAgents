package casa;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.*;
import jadex.runtime.*;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasOrinadoSuelo;

/**
 * Created by eldgb on 02-Nov-17.
 */
public class OrinarSueloPlan extends Plan {

    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        OrinarSuelo content = (OrinarSuelo) peticion.getContent();
        HasOrinadoSuelo response = new HasOrinadoSuelo();

        Higiene h = content.getHigiene();
        h.setGrado(content.getHigiene().getGrado() - Necesidad.NC_MUCHO);
        response.setHigiene(h);

        Diversion d = content.getDiversion();
        d.setGrado(content.getDiversion().getGrado() - Necesidad.NC_NORMAL);
        response.setDiversion(d);

        Vejiga v = content.getVejiga();
        v.setGrado(content.getVejiga().getGrado() + Necesidad.NC_NORMAL);
        response.setVejiga(v);




        try {
            wait(Accion.TIEMPO_LARGO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        IMessageEvent respuesta = createMessageEvent("has_dormido_suelo");
        respuesta.setContent(response);
        sendMessage(respuesta);
    }
}
