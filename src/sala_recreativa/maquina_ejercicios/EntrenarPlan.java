package sala_recreativa.maquina_ejercicios;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.Entrenar;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class EntrenarPlan extends Plan{
    public void body() {
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Entrenar content = (Entrenar)peticion.getContent();
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();

        if(ocupado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("maquina_ejercicios_ocupada");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            int grado_e = content.getEnergia().getGrado();
            int grado_h = content.getHigiene().getGrado();
            int grado_hmb = content.getHambre().getGrado();
            int experiencia_d = content.getDeporte().getExperiencia();
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;

            getBeliefbase().getBelief("energia").setFact(new Integer(grado_e));
            getBeliefbase().getBelief("higiene").setFact(new Integer(grado_h));
            getBeliefbase().getBelief("hambre").setFact(new Integer(grado_hmb));
            getBeliefbase().getBelief("deporte").setFact(new Integer(experiencia_d));
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));

        }

    }
}
