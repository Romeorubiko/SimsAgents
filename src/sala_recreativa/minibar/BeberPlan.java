package sala_recreativa.minibar;
import jadex.runtime.IMessageEvent;
import ontologia.Accion;
import ontologia.conceptos.necesidades.*;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.acciones.Beber;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class BeberPlan extends Plan {
    public void body() {
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Beber content = (Beber)peticion.getContent();
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();

        if(ocupado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("minibar_ocupado");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            int grado_e = content.getEnergia().getGrado();
            int grado_d = content.getDiversion().getGrado();
            int grado_v = content.getVejiga().getGrado();
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_CORTO;

            getBeliefbase().getBelief("energia").setFact(new Integer(grado_e));
            getBeliefbase().getBelief("diversion").setFact(new Integer(grado_d));
            getBeliefbase().getBelief("vejiga").setFact(new Integer(grado_v));
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));


        }
    }
}
