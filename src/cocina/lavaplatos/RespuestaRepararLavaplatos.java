package cocina.lavaplatos;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.acciones.*;
import ontologia.predicados.*;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

public class RespuestaRepararLavaplatos extends Plan {

	public void body() {

		int new_timer = (int) (System.currentTimeMillis() + 100000);
		getBeliefbase().getBelief("tiempo_fin_reparar_lavaplatos").setFact(new Integer(new_timer));

		IMessageEvent request = (IMessageEvent) getBeliefbase().getBelief("mensaje_reparar_lavaplatos").getFact();
		Reparar contenido = (Reparar) request.getContent();

		Higiene higiene = contenido.getHigiene();
		Energia energia = contenido.getEnergia();
		Mecanica mecanica = contenido.getMecanica();

		higiene.setGrado(higiene.getGrado() - Necesidad.NC_NORMAL);
		energia.setGrado(energia.getGrado() - Necesidad.NC_NORMAL / mecanica.getNivel());
		mecanica.setExperiencia(mecanica.getExperiencia() + Habilidad.HB_MUCHO);

		HasReparado response = new HasReparado(energia, higiene, mecanica);

		getBeliefbase().getBelief("obsolescencia").setFact(new Integer(10));
		
		IMessageEvent inform = createMessageEvent("lavaplatos_reparado");
		inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameter("sender").getValue());
		inform.setContent(response);
	
		getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
	
		sendMessage(inform);
	}
}