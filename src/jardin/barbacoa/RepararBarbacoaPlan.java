package jardin.barbacoa;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.Reparar;



/**
 * Created by eldgb on 09-Nov-17.
 */
public class RepararBarbacoaPlan extends Plan {
    public void body() {

        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Reparar content = (Reparar) peticion.getContent();

        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean estropeado = (Boolean)getBeliefbase().getBelief("estropeado").getFact();

        if(ocupado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("barbacoa_ocupada_reparar");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }

        else if (!estropeado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("barbacoa_no_estropeada");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            int grado_e = content.getEnergia().getGrado();
            int grado_h = content.getHigiene().getGrado();
            int experiencia_m = content.getMecanica().getExperiencia();
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;

            getBeliefbase().getBelief("energia").setFact(new Integer(grado_e));
            getBeliefbase().getBelief("higiene").setFact(new Integer(grado_h));
            getBeliefbase().getBelief("experiencia_mecanica").setFact(new Integer(experiencia_m));
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));

        }
    }
}
