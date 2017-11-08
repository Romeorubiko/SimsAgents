package dormitorio.cama;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.*;
import jadex.runtime.*;

import ontologia.conceptos.necesidades.Energia;

/**
 * Created by eldgb on 08-Nov-17.
 */

public class DescansarPlan extends Plan {
    public void body(){
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Descansar content = (Descansar)peticion.getContent();
        Energia e = content.getEnergia();
        int grado_energia = e.getGrado();

        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();

        if(ocupado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("cama_ocupada");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            getBeliefbase().getBelief("energia").setFact(new Integer(grado_energia));

            end_timer = System.currentTimeMillis() + Accion.TIEMPO_LARGO;


        }
    }





}
