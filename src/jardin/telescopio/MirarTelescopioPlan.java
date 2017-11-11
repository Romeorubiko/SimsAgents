package jardin.telescopio;
import ontologia.Accion;
import ontologia.acciones.*;
import jadex.runtime.*;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.impl.RMessageEvent;

public class MirarTelescopioPlan extends Plan{
    public void body() {
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("telescopio_ocupado");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            IMessageEvent agree = createMessageEvent("telescopio_no_ocupado");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);
            getBeliefbase().getBelief("mensaje_mirar_telescopio").setFact(peticion);
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;
            getBeliefbase().getBelief("tiempo_fin_mirar_telescopio").setFact(new Integer(end_timer));
            IGoal goal= createGoal("terminar_mirar_telescopio");
            dispatchSubgoal(goal);
        }
    }
}