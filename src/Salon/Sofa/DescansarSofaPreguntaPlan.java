package Salon.Sofa;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.Descansar;
import ontologia.conceptos.Descanso;

public class DescansarSofaPreguntaPlan extends Plan {

    public DescansarSofaPreguntaPlan() {

    }

    /**
     *  Plan body.
     */
    public void body()
    {
        IMessageEvent request	= (IMessageEvent)getInitialEvent();
		/* Creencias */
        RBeliefbase bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_sofa");
        Boolean ocupado= (Boolean)creenciaOcupado.getFact();

        if(ocupado){
            IMessageEvent refuse = createMessageEvent("sofa_ocupado");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }else{
            creenciaOcupado.setFact(true);
            Descansar content = (Descansar)request.getContent();
            Descanso tipo= content.getTipo();

            RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_sofa");
            RBelief creenciaTiempoSofa=(RBelief) bb.getBelief("tiempo_fin_sofa");
            RBelief creenciaTiempo=(RBelief) bb.getBelief("tiempo_actual");
            Integer tiempo= (Integer)creenciaTiempo.getFact();

            creenciaMensaje.setFact(request);
            if(tipo.getTipoDescanso().equals(Descanso.tipoDescanso.DORMIR)){
                creenciaTiempoSofa.setFact(tiempo + Accion.TIEMPO_LARGO);
            }else{
                creenciaTiempoSofa.setFact(tiempo + Accion.TIEMPO_MEDIO);
            }

            IMessageEvent agree = createMessageEvent("sofa_no_ocupado");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            IGoal goal= createGoal("descansar_sofa_tiempo_superado");
            dispatchTopLevelGoal(goal);
        }
    }
}
