package cocina.cafetera;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;

import ontologia.acciones.Beber;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.CafeteraEstropeada;

public class PermisoBeberCafePlan extends Plan {
	
	public PermisoBeberCafePlan() {

	}

	@Override
	public void body() {
		IMessageEvent request = (IMessageEvent) getInitialEvent();
		RBeliefbase creencias = (RBeliefbase) getBeliefbase();

		Boolean ocupado = (Boolean) creencias.getBelief("ocupado").getFact();

		if (ocupado) {
			IMessageEvent refuse = createMessageEvent("cafetera_ocupada");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(refuse);
		} else {
			creencias.getBelief("ocupado").setFact(true);

			int obsolescencia = (int) creencias.getBelief("obsolescencia").getFact();

			if (obsolescencia <= 0) {
				Beber content = (Beber) request.getContent();

				Diversion diversion = content.getDiversion();
				Vejiga vejiga = content.getVejiga();
				Energia energia = content.getEnergia();

				IMessageEvent failure = createMessageEvent("cafetera_estropeada");
				CafeteraEstropeada estropeado = new CafeteraEstropeada();
				estropeado.setDiversion(diversion);
				estropeado.setEnergia(energia);
				estropeado.setVejiga(vejiga);;

				failure.setContent(estropeado);
				failure.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
				sendMessage(failure);
			} else {
				obsolescencia--;
				creencias.getBelief("obsolescencia").setFact(obsolescencia);

				RBelief mensajeCafetera = (RBelief) creencias.getBelief("mensaje_cafetera");
				RBelief tiempoFin = (RBelief) creencias.getBelief("tiempo_fin");
				RBelief tiempoLavaplatos = (RBelief) creencias.getBelief("tiempo_cafetera");
				Integer tiempo = (Integer) tiempoLavaplatos.getFact();

				mensajeCafetera.setFact(request);
				tiempoFin.setFact(tiempo + Accion.TIEMPO_CORTO);

				IMessageEvent agree = createMessageEvent("cafetera_no_ocupada");
				agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
				sendMessage(agree);

				IGoal goal = createGoal("tiempo_superado");
				dispatchTopLevelGoal(goal);
			}
		}
	}

}
