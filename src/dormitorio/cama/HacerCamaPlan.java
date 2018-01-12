/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */


package dormitorio.cama;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.HacerLaCama;


public class HacerCamaPlan extends Plan {
    public void body() {

        IMessageEvent peticion = (IMessageEvent)getInitialEvent();
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean hecha = (Boolean) getBeliefbase().getBelief("cama_hecha").getFact();
        HacerLaCama content = (HacerLaCama) peticion.getContent();

        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("cama_ocupada");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            refuse.setContent(content);
            sendMessage(refuse);;
        }
        else if (hecha.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("cama_ya_hecha");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            refuse.setContent(content);
            sendMessage(refuse);
        }

        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            IMessageEvent agree = createMessageEvent("cama_no_ocupada");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            agree.setContent(content);
            sendMessage(agree);

            getBeliefbase().getBelief("mensaje_hacer_cama").setFact(peticion);

            int end_timer = (int) System.currentTimeMillis()/1000 + Accion.TIEMPO_CORTO;
            getBeliefbase().getBelief("tiempo_fin_hacer_cama").setFact(new Integer(end_timer));

            /*IGoal goal= createGoal("terminar_hacer_cama");
            dispatchSubgoal(goal);*/
        }
    }
}
