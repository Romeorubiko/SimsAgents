package jardin.silla;
import ontologia.Accion;
import ontologia.acciones.*;
import jadex.runtime.*;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.impl.RMessageEvent;

public class SentarseSillaPlan extends Plan {
    public void body(){
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("silla_ocupada");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            IMessageEvent agree = createMessageEvent("silla_no_ocupada");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);
            getBeliefbase().getBelief("mensaje_sentarse_silla").setFact(peticion);
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_CORTO;
            getBeliefbase().getBelief("tiempo_fin_sentarse_silla").setFact(new Integer(end_timer));
            IGoal goal= createGoal("terminar_sentarse_silla");
            dispatchSubgoal(goal);
        }
    }
}
