package cocina.fogones;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.CocinarComida;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasCocinado;
import ontologia.predicados.FogonesEstropeados;

public class cocinarComidaRespuestaPlan extends Plan {
    public cocinarComidaRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje").getFact();
        getBeliefbase().getBelief("tiempo_fin").setFact(0);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getGoalbase().getGoal("cocinar_comida_tiempo_superado").drop();

        CocinarComida content = (CocinarComida) peticion.getContent();

        Energia energia = content.getEnergia();
        Hambre hambre = content.getHambre();

        energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
        content.setEnergia(energia);

        hambre.setGrado(hambre.getGrado() + Necesidad.NC_NORMAL);
        content.setHambre(hambre);

        IMessageEvent respuesta = createMessageEvent("has_cocinado");
        HasCocinado hasCocinado = new HasCocinado(energia, hambre);
        respuesta.setContent(hasCocinado);
        sendMessage(respuesta);
    }
}
