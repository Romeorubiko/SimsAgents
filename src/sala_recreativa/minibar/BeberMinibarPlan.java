/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */

package sala_recreativa.minibar;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import ontologia.Accion;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.acciones.Beber;


public class BeberMinibarPlan extends Plan {
    public void body() {

        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Beber content = (Beber) peticion.getContent();

        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("minibar_ocupado");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            refuse.setContent(content);
            sendMessage(refuse);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            IMessageEvent agree = createMessageEvent("minibar_no_ocupada");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            agree.setContent(content);
            sendMessage(agree);

            getBeliefbase().getBelief("mensaje_beber_minibar").setFact(peticion);

            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_CORTO;
            getBeliefbase().getBelief("tiempo_fin_minibar").setFact(new Integer(end_timer));

            /*IGoal goal= createGoal("terminar_beber_minibar");
            dispatchSubgoal(goal);*/



        }
    }
}
