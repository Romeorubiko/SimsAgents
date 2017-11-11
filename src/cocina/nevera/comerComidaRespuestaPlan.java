package cocina.nevera;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.ComerComida;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasComido;
import ontologia.predicados.NeveraEstropeadaComer;

public class comerComidaRespuestaPlan extends Plan {
    public comerComidaRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje").getFact();
        getBeliefbase().getBelief("tiempo_fin").setFact(0);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getGoalbase().getGoal("comer_comida_tiempo_superado").drop();

        ComerComida content = (ComerComida) peticion.getContent();

        Energia energia = content.getEnergia();
        Hambre hambre = content.getHambre();

        energia.setGrado(energia.getGrado() + Necesidad.NC_POCO);
        content.setEnergia(energia);

        hambre.setGrado(hambre.getGrado() + Necesidad.NC_NORMAL);
        content.setHambre(hambre);

        IMessageEvent respuesta = createMessageEvent("has_comido");
        HasComido hasComido = new HasComido(energia, hambre);
        respuesta.setContent(hasComido);
        sendMessage(respuesta);
    }
}
