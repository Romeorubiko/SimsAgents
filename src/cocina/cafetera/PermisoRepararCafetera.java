package cocina.cafetera;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;

public class PermisoRepararCafetera extends Plan {

	public void body() {

		IMessageEvent request = (IMessageEvent) getInitialEvent();
		RBeliefbase creencias = (RBeliefbase) getBeliefbase();
		int obsolescencia = (int) creencias.getBelief("obsolescencia").getFact();
		Boolean ocupado = (Boolean) creencias.getBelief("ocupado").getFact();

		if (ocupado.booleanValue()) {
			IMessageEvent refuse = createMessageEvent("cafetera_ocupada");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet("sender").getValues());
			refuse.setContent(request.getContent());
			sendMessage(refuse);
		} else if (obsolescencia > 0) {
			IMessageEvent refuse = createMessageEvent("cafetera_no_estropeada");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet("sender").getValues());
			refuse.setContent(request.getContent());
			sendMessage(refuse);

		} else {
			creencias.getBelief("ocupado").setFact(Boolean.TRUE);

			creencias.getBelief("mensaje_reparar_cafetera").setFact(request);

			int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;
			creencias.getBelief("tiempo_fin_reparar_cafetera").setFact(new Integer(end_timer));

			IMessageEvent agree = createMessageEvent("puedes_reparar_cafetera");
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet("sender").getValues());
			agree.setContent(request.getContent());
			sendMessage(agree);
		}
	}
}
