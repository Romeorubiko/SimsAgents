package sala_recreativa.guitarra;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;


public class TocarGuitarraPregunta extends Plan {

    /**
     *  Plan body.
     */
    public void body()
    {
        /* Creencias */
        IMessageEvent   request = (IMessageEvent)getInitialEvent();
        RBeliefbase bb;
        bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_guitarra");
        Boolean ocupado= (Boolean)creenciaOcupado.getFact();
        

        if(ocupado){
            IMessageEvent refuse = createMessageEvent("guitarra_ocupado");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }else{
            RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_tocar_guitarra");
            RBelief creenciaTiempoFinGuitarra=(RBelief) bb.getBelief("tiempo_fin_guitarra");
            RBelief creenciaTiempo=(RBelief) bb.getBelief("tiempo_guitarra");
            Integer tiempo= (Integer)creenciaTiempo.getFact();

            creenciaMensaje.setFact(request);
            creenciaTiempoFinGuitarra.setFact(tiempo + Accion.TIEMPO_CORTO);
            creenciaOcupado.setFact(true);

            IMessageEvent agree = createMessageEvent("guitarra_no_ocupado");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            IGoal goal= createGoal("tiempo_guitarra_superado");
            dispatchSubgoal(goal);
        }
    }
}