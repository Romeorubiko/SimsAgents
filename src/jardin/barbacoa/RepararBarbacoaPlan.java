/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */

package jardin.barbacoa;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
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
            IMessageEvent refuse = createMessageEvent("barbacoa_ocupada");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            refuse.setContent(content);
            sendMessage(refuse);
        }

        else if (!estropeado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("barbacoa_no_estropeada");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            refuse.setContent(content);
            sendMessage(refuse);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            IMessageEvent agree = createMessageEvent("barbacoa_cocinar_no_ocupada");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            agree.setContent(content);
            sendMessage(agree);

            getBeliefbase().getBelief("mensaje_reparar_barbacoa").setFact(peticion);


            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;
            getBeliefbase().getBelief("tiempo_fin_reparar_barbacoa").setFact(new Integer(end_timer));

            /*IGoal goal= createGoal("terminar_reparar_barbacoa");
            dispatchSubgoal(goal);*/

        }
    }
}
