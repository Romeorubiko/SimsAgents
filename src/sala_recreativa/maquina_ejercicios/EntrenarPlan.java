package sala_recreativa.maquina_ejercicios;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import jardin.barbacoa.RepararBarbacoaPlan;
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
            IMessageEvent refuse = createMessageEvent("maquina_ejercicios_ocupada");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            IMessageEvent agree = createMessageEvent("maquina_ejercicios_no_ocupada");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            getBeliefbase().getBelief("mensaje_entrenar").setFact(peticion);

            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;
            getBeliefbase().getBelief("tiempo_fin_entrenar").setFact(new Integer(end_timer));

            IGoal goal= createGoal("terminar_entrenar");
            dispatchSubgoal(goal);

        }

    }
}
