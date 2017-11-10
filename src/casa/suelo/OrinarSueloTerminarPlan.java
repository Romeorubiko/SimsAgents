package casa.suelo;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.acciones.OrinarSuelo;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.HasOrinadoSuelo;

public class OrinarSueloTerminarPlan extends Plan {

	@Override
	public void body() {

		getGoalbase().getGoal("terminar_orinar_suelo").drop();
		getBeliefbase().getBelief("tiempo_fin_orinar_suelo").setFact(new Integer(0));

		RMessageEvent peticion = (RMessageEvent) getBeliefbase().getBelief("mensaje_orinar_suelo").getFact();
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

		IMessageEvent inform = createMessageEvent("has_orinado_suelo");
		inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
		inform.setContent(response);
		sendMessage(inform);

	}

}
