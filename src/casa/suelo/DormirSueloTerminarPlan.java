
/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */

package casa.suelo;

import java.util.ArrayList;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.acciones.DormirSuelo;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasDormidoSuelo;

public class DormirSueloTerminarPlan extends Plan {

	@SuppressWarnings("unchecked")
	@Override
	public void body() {

		if (((ArrayList<IMessageEvent>) getBeliefbase().getBelief("mensajes_dormir_suelo").getFact()).size() == 1) {
			getGoalbase().getGoal("terminar_dormir_suelo").drop();
		}

		IMessageEvent peticion = ((ArrayList<IMessageEvent>) getBeliefbase()
				.getBelief("mensajes_dormir_suelo").getFact()).get(0);
		DormirSuelo content = (DormirSuelo) peticion.getContent();
		HasDormidoSuelo response = new HasDormidoSuelo();

		Higiene h = content.getHigiene();
		h.setGrado(content.getHigiene().getGrado() - Necesidad.NC_MUCHO);
		response.setHigiene(h);

		Diversion d = content.getDiversion();
		d.setGrado(content.getDiversion().getGrado() - Necesidad.NC_NORMAL);
		response.setDiversion(d);

		Energia e = content.getEnergia();
		e.setGrado(content.getEnergia().getGrado() + Necesidad.NC_NORMAL);
		response.setEnergia(e);

		ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) getBeliefbase()
				.getBelief("mensajes_dormir_suelo").getFact();
		arrayMensajes.remove(0);
		getBeliefbase().getBelief("mensajes_dormir_suelo").setFact(arrayMensajes);

		ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) getBeliefbase().getBelief("tiempos_dormir_suelo")
				.getFact();
		arrayTiempos.remove(0);
		getBeliefbase().getBelief("tiempos_dormir_suelo").setFact(arrayTiempos);

		IMessageEvent inform = createMessageEvent("has_dormido_suelo");
		inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
		inform.setContent(response);
		sendMessage(inform);

	}
}
