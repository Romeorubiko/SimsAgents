package cocina.lavaplatos;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;

public class PermisoLavarPlatosPlan extends Plan {

	@Override
	public void body() {
		IMessageEvent request = (IMessageEvent) getInitialEvent();
		RBeliefbase creencias = (RBeliefbase) getBeliefbase();
		int obsolescencia = (int) creencias.getBelief("obsolescencia").getFact();
		Boolean ocupado = (Boolean) creencias.getBelief("ocupado").getFact();

		if (ocupado) {
			IMessageEvent refuse = createMessageEvent("lavaplatos_ocupado");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet("sender").getValues());
			refuse.setContent(request.getContent());
			sendMessage(refuse);
		} else if (obsolescencia <= 0) {
			IMessageEvent refuse = createMessageEvent("lavaplatos_estropeado");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet("sender").getValues());
			refuse.setContent(request.getContent());
			sendMessage(refuse);

		} else {
			obsolescencia--;
			creencias.getBelief("obsolescencia").setFact(obsolescencia);
			creencias.getBelief("ocupado").setFact(Boolean.TRUE);

			creencias.getBelief("mensaje_lavar_platos").setFact(request);

			int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_LARGO;
			creencias.getBelief("tiempo_fin_lavar_platos").setFact(new Integer(end_timer));

			IMessageEvent agree = createMessageEvent("puedes_lavar_platos");
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet("sender").getValues());
			agree.setContent(request.getContent());
			sendMessage(agree);

		}

	}
}
