package casa.suelo;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;


/**
 * Created by eldgb on 25-Oct-17.
 */
public class DormirSueloPlan extends Plan {

    public void body(){
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());

        IMessageEvent agree = createMessageEvent("suelo_no_ocupado");
        agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
        sendMessage(agree);
        
        getBeliefbase().getBelief("mensaje_dormir_suelo").setFact(peticion);

        int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_LARGO;
        getBeliefbase().getBelief("tiempo_fin_dormir_suelo").setFact(new Integer(end_timer));

        IGoal goal= createGoal("terminar_dormir_suelo");
        dispatchSubgoal(goal);

    }
}
