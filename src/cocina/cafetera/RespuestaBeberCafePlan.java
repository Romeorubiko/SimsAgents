package cocina.cafetera;


import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBeliefbase;

import ontologia.acciones.Beber;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasBebido;


public class RespuestaBeberCafePlan  extends Plan{
	
	public RespuestaBeberCafePlan() {

	}
	private Diversion diversion;
	private Vejiga vejiga;
	private Energia energia;
	
	@Override
	public void body() {
		RBeliefbase creencias = (RBeliefbase) getBeliefbase();
		IMessageEvent request = (IMessageEvent) creencias.getBelief("mensaje_cafetera").getFact();
		Beber content = (Beber) request.getContent();

		diversion.setGrado(content.getDiversion().getGrado());
		
		vejiga.setGrado(content.getVejiga().getGrado() + Necesidad.NC_NORMAL);
		
		energia.setGrado(content.getEnergia().getGrado() + Necesidad.NC_MUCHO);

		IMessageEvent respuesta = createMessageEvent("cafe_bebido");
		HasBebido hasBebido = new HasBebido();
		hasBebido.setDiversion(diversion);
		hasBebido.setEnergia(energia);
		hasBebido.setVejiga(vejiga);
		respuesta.setContent(hasBebido);
		sendMessage(respuesta);
		getBeliefbase().getBelief("ocupado").setFact(false);

	}

}
