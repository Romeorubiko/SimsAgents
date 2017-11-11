/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */


package dormitorio.cama;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import jadex.runtime.*;


public class DescansarCamaPlan extends Plan {
    public void body(){

        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();

        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("cama_ocupada");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            IMessageEvent agree = createMessageEvent("cama_no_ocupada");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            getBeliefbase().getBelief("mensaje_descansar_cama").setFact(peticion);

            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_LARGO;
            getBeliefbase().getBelief("tiempo_fin_descansar_cama").setFact(new Integer(end_timer));

            IGoal goal= createGoal("terminar_descansar_cama");
            dispatchSubgoal(goal);
        }
    }





}
