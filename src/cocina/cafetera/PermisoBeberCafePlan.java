package cocina.cafetera;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;


public class PermisoBeberCafePlan extends Plan {

	public PermisoBeberCafePlan() {

	}

	@Override
	public void body() {
		IMessageEvent request = (IMessageEvent) getInitialEvent();
		RBeliefbase creencias = (RBeliefbase) getBeliefbase();
		int obsolescencia = (int) creencias.getBelief("obsolescencia").getFact();
		Boolean ocupado = (Boolean) creencias.getBelief("ocupado").getFact();

		if (ocupado) {
			IMessageEvent refuse = createMessageEvent("cafetera_ocupada");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet("sender").getValues());
			refuse.setContent(request.getContent());
			sendMessage(refuse);
		} else if (obsolescencia <= 0) {
			IMessageEvent refuse = createMessageEvent("cafetera_estropeada");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet("sender").getValues());
			refuse.setContent(request.getContent());
			sendMessage(refuse);

		} else {
			obsolescencia--;
			creencias.getBelief("obsolescencia").setFact(obsolescencia);
			creencias.getBelief("ocupado").setFact(Boolean.TRUE);

			creencias.getBelief("mensaje_beber_cafe").setFact(request);

			int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_CORTO;
			creencias.getBelief("tiempo_fin_beber_cafe").setFact(new Integer(end_timer));

			IMessageEvent agree = createMessageEvent("puedes_beber_cafe");
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet("sender").getValues());
			agree.setContent(request.getContent());
			sendMessage(agree);

		}
	}
}
