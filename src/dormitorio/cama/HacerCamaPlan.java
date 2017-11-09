package dormitorio.cama;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.HacerLaCama;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class HacerCamaPlan extends Plan {
    public void body() {
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        HacerLaCama content = (HacerLaCama) peticion.getContent();
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean hecha = (Boolean) getBeliefbase().getBelief("cama_hecha").getFact();

        if(ocupado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("hacer_cama_ocupada");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        else if (hecha.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("cama_ya_hecha");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }

        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            int grado_h = content.getHigiene().getGrado();
            int grado_e = content.getEnergia().getGrado();
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_CORTO;

            getBeliefbase().getBelief("energia").setFact(new Integer(grado_e));
            getBeliefbase().getBelief("higiene").setFact(new Integer(grado_h));
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));
        }
    }
}
