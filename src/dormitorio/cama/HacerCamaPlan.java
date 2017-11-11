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



public class HacerCamaPlan extends Plan {
    public void body() {

        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean hecha = (Boolean) getBeliefbase().getBelief("cama_hecha").getFact();

        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("cama_ocupada");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);;
        }
        else if (hecha.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("cama_ya_hecha");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }

        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            IMessageEvent agree = createMessageEvent("cama_no_ocupada");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            getBeliefbase().getBelief("mensaje_hacer_cama").setFact(peticion);

            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_CORTO;
            getBeliefbase().getBelief("tiempo_fin_hacer_cama").setFact(new Integer(end_timer));

            IGoal goal= createGoal("terminar_hacer_cama");
            dispatchSubgoal(goal);
        }
    }
}
