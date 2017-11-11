package cocina.lavaplatos;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.LavarPlatos;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.predicados.LavaplatosEstropeado;

public class PermisoLavarPlatosPlan extends Plan {

	public void PermisoLavarPlatosPlan() {

	}

	@Override
	public void body() {
		IMessageEvent request = (IMessageEvent) getInitialEvent();
		RBeliefbase creencias = (RBeliefbase) getBeliefbase();

		Boolean ocupado = (Boolean) creencias.getBelief("ocupado").getFact();

		if (ocupado) {
			IMessageEvent refuse = createMessageEvent("lavaplatos_ocupado");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(refuse);
		} else {
			creencias.getBelief("ocupado").setFact(true);

			int obsolescencia = (int) creencias.getBelief("obsolescencia").getFact();

			if (obsolescencia <= 0) {
				LavarPlatos content = (LavarPlatos) request.getContent();

				Diversion diversion = content.getDiversion();
				Higiene higiene = content.getHigiene();
				Energia energia = content.getEnergia();

				IMessageEvent failure = createMessageEvent("lavaplatos_estropeado");
				LavaplatosEstropeado estropeado = new LavaplatosEstropeado();
				estropeado.setDiversion(diversion);
				estropeado.setEnergia(energia);
				estropeado.setHigiene(higiene);

				failure.setContent(estropeado);
				failure.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
				sendMessage(failure);
			} else {
				obsolescencia--;
				creencias.getBelief("obsolescencia").setFact(obsolescencia);

				RBelief mensajeLavaplatos = (RBelief) creencias.getBelief("mensaje_lavaplatos");
				RBelief tiempoFin = (RBelief) creencias.getBelief("tiempo_fin");
				RBelief tiempoLavaplatos = (RBelief) creencias.getBelief("tiempo_lavaplatos");
				Integer tiempo = (Integer) tiempoLavaplatos.getFact();

				mensajeLavaplatos.setFact(request);
				tiempoFin.setFact(tiempo + Accion.TIEMPO_MEDIO);

				IMessageEvent agree = createMessageEvent("lavaplatos_no_ocupado");
				agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
				sendMessage(agree);

				IGoal goal = createGoal("tiempo_superado");
				dispatchTopLevelGoal(goal);
			}

		}
	}
}
