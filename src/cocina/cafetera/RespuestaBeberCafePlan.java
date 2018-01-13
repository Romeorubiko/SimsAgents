package cocina.cafetera;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;

import ontologia.acciones.Beber;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasBebido;

public class RespuestaBeberCafePlan extends Plan {

	public RespuestaBeberCafePlan() {

	}

	@Override
	public void body() {

		int new_timer = (int) (System.currentTimeMillis() + 100000);
		getBeliefbase().getBelief("tiempo_fin_reparar_cafetera").setFact(new Integer(new_timer));
		IMessageEvent request = (IMessageEvent) getBeliefbase().getBelief("mensaje_cafetera").getFact();
		Beber content = (Beber) request.getContent();

		Diversion diversion = content.getDiversion();
		Vejiga vejiga = content.getVejiga();
		Energia energia = content.getEnergia();

		diversion.setGrado(content.getDiversion().getGrado());

		vejiga.setGrado(content.getVejiga().getGrado() + Necesidad.NC_NORMAL);

		energia.setGrado(content.getEnergia().getGrado() + Necesidad.NC_MUCHO);
		
		HasBebido hasBebido = new HasBebido();
		hasBebido.setDiversion(diversion);
		hasBebido.setEnergia(energia);
		hasBebido.setVejiga(vejiga);
		
		IMessageEvent respuesta = createMessageEvent("cafe_bebido");
		respuesta.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameter("sender").getValue());
		respuesta.setContent(hasBebido);
		
		getBeliefbase().getBelief("ocupado").setFact(false);

		sendMessage(respuesta);
	}

}
