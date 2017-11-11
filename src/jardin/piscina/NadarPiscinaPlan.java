package jardin.piscina;
import ontologia.Accion;
import ontologia.acciones.*;
import jadex.runtime.*;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.impl.RMessageEvent;

public class NadarPiscinaPlan extends Plan{
    public void body() {
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("piscina_ocupada");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            IMessageEvent agree = createMessageEvent("piscina_no_ocupada");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);
            getBeliefbase().getBelief("mensaje_nadar").setFact(peticion);
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;
            getBeliefbase().getBelief("tiempo_fin_nadar").setFact(new Integer(end_timer));
            IGoal goal= createGoal("terminar_nadar");
            dispatchSubgoal(goal);
        }
    }
}