/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */

package sala_recreativa.maquina_ejercicios;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.Entrenar;


public class EntrenarPlan extends Plan{
    public void body() {
        IMessageEvent peticion = (IMessageEvent)getInitialEvent();
        Entrenar content = (Entrenar)peticion.getContent();
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();

        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("maquina_ejercicios_ocupada");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            refuse.setContent(content);
            sendMessage(refuse);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            IMessageEvent agree = createMessageEvent("maquina_ejercicios_no_ocupada");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            agree.setContent(content);
            sendMessage(agree);

            getBeliefbase().getBelief("mensaje_entrenar").setFact(peticion);

            int end_timer = (int) (System.currentTimeMillis()/1000) + Accion.TIEMPO_MEDIO;
            getBeliefbase().getBelief("tiempo_fin_entrenar").setFact(new Integer(end_timer));


        }

    }
}
