package Salon.Sofa;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.Descansar;
import ontologia.conceptos.Descanso;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasDescansado;

public class DescansarSofaRespuestaPlan extends Plan {
    /**
     *  Plan body.
     */
    public void body()
    {
        RBeliefbase bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_sofa");
        RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_sofa");
        IMessageEvent request= (IMessageEvent) creenciaMensaje.getFact();
        RBelief creenciaTiempoSofa=(RBelief) bb.getBelief("tiempo_fin_sofa");
        creenciaTiempoSofa.setValue(0);
        getGoalbase().getGoal("descansar_sofa_tiempo_superado").drop();


        Descansar content = (Descansar)request.getContent();
        Descanso tipo= content.getTipo();
        Energia energia= content.getEnergia();

        /*
        Energía segun tipo de descnaso
         */
        if(tipo.getTipoDescanso().equals(Descanso.tipoDescanso.DORMIR)){
            energia.setGrado(content.getEnergia().getGrado()+ Necesidad.NC_MUCHO);
            content.setEnergia(energia);
        }else{
            energia.setGrado(content.getEnergia().getGrado()+Necesidad.NC_NORMAL);
            content.setEnergia(energia);
        }


        IMessageEvent inform = createMessageEvent("has_descansado");
        HasDescansado hasDescansado= new HasDescansado(energia);
        inform.setContent(hasDescansado);
        inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
        sendMessage(inform);
        creenciaOcupado.setFact(false);
    }

}
