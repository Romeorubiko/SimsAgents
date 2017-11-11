package casa.suelo;

import java.util.ArrayList;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.acciones.OrinarSuelo;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.HasOrinadoSuelo;

public class OrinarSueloTerminarPlan extends Plan {

	@SuppressWarnings("unchecked")
	@Override
	public void body() {
		if (((ArrayList<IMessageEvent>) getBeliefbase().getBelief("mensajes_orinar_suelo").getFact()).size() == 1) {
			getGoalbase().getGoal("terminar_orinar_suelo").drop();
		}

		IMessageEvent peticion = ((ArrayList<IMessageEvent>) getBeliefbase()
				.getBelief("mensajes_orinar_suelo").getFact()).get(0);
		OrinarSuelo content = (OrinarSuelo) peticion.getContent();
		HasOrinadoSuelo response = new HasOrinadoSuelo();

		Higiene h = content.getHigiene();
		h.setGrado(content.getHigiene().getGrado() - Necesidad.NC_MUCHO);
		response.setHigiene(h);

		Diversion d = content.getDiversion();
		d.setGrado(content.getDiversion().getGrado() - Necesidad.NC_NORMAL);
		response.setDiversion(d);

		Vejiga v = content.getVejiga();
		v.setGrado(content.getVejiga().getGrado() + Necesidad.NC_NORMAL);
		response.setVejiga(v);

		ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) getBeliefbase()
				.getBelief("mensajes_orinar_suelo").getFact();
		arrayMensajes.remove(0);
		getBeliefbase().getBelief("mensajes_orinar_suelo").setFact(arrayMensajes);

		ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) getBeliefbase().getBelief("tiempos_orinar_suelo")
				.getFact();
		arrayTiempos.remove(0);
		getBeliefbase().getBelief("tiempos_orinar_suelo").setFact(arrayTiempos);

		IMessageEvent inform = createMessageEvent("has_orinado_suelo");
		inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
		inform.setContent(response);
		sendMessage(inform);

	}

}
