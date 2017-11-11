package cocina.nevera;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.Beber;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasBebido;
import ontologia.predicados.NeveraEstropeadaBeber;

public class beberRespuestaPlan extends Plan {
    public beberRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje").getFact();
        getBeliefbase().getBelief("tiempo_fin").setFact(0);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getGoalbase().getGoal("beber_tiempo_superado").drop();

        Beber content = (Beber) peticion.getContent();

        Energia energia = content.getEnergia();
        Vejiga vejiga = content.getVejiga();
		Diversion diversion = content.getDiversion();

        energia.setGrado(energia.getGrado() + Necesidad.NC_POCO);
        content.setEnergia(energia);

        vejiga.setGrado(vejiga.getGrado() - Necesidad.NC_NORMAL);
        content.setVejiga(vejiga);
		
		diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
        content.setDiversion(diversion);

        IMessageEvent respuesta = createMessageEvent("has_bebido");
        HasBebido hasBebido = new HasBebido(energia, vejiga, diversion);
        respuesta.setContent(hasBebido);
        sendMessage(respuesta);
    }
}