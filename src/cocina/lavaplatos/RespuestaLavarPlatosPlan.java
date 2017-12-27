package cocina.lavaplatos;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBeliefbase;

import ontologia.acciones.LavarPlatos;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.PlatosLavados;

public class RespuestaLavarPlatosPlan extends Plan {

	private Energia energia;
	private Diversion diversion;
	private Higiene higiene;

	public RespuestaLavarPlatosPlan() {

	}

	@Override
	public void body() {
		RBeliefbase creencias = (RBeliefbase) getBeliefbase();
		IMessageEvent request = (IMessageEvent) creencias.getBelief("mensaje_lavaplatos").getFact();
		LavarPlatos content = (LavarPlatos) request.getContent();

		diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_POCO);

		higiene.setGrado(content.getHigiene().getGrado() + Necesidad.NC_NORMAL);

		energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);

		IMessageEvent respuesta = createMessageEvent("platos_lavados");
		PlatosLavados PlatosLavados = new PlatosLavados();
		PlatosLavados.setDiversion(diversion);
		PlatosLavados.setEnergia(energia);
		PlatosLavados.setHigiene(higiene);
		respuesta.setContent(PlatosLavados);
		sendMessage(respuesta);
		getBeliefbase().getBelief("ocupado").setFact(false);

	}

}
