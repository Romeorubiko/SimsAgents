package Salon.Telefono;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import ontologia.acciones.LlamarSim;
import ontologia.conceptos.habilidades.Carisma;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasLlamado;

public class LlamarSimRespuestaPlan extends Plan {

	public LlamarSimRespuestaPlan() {	
	}
	
	public void body() {
		/* Obtenci n de las creencias */
		RBelief creenciaOcupado = (RBelief) getBeliefbase().getBelief("telefono_ocupado");
		RBelief creenciaMensaje = (RBelief) getBeliefbase().getBelief("mensaje");
		RBelief creenciaTiempoLlamada = (RBelief) getBeliefbase().getBelief("tiempo_fin_llamada");
		
		/* Se recupera el contenido del request guardado en el primer plan a trav s de una creencia */
		IMessageEvent request = (IMessageEvent) creenciaMensaje.getFact();
		LlamarSim content = (LlamarSim) request.getContent();
		InteraccionSocial interaccion = content.getInteraccionSocial();
		Energia energia = content.getEnergia();
		Carisma carisma = content.getCarisma();
		
		/* La llamada ha terminado, por lo que se resetea el valor de la creencia del tiempo de la llamada a 0 */
		creenciaTiempoLlamada.setValue(0);
		
		/* Se desactiva el objetivo que ha causado la ejecuci n de este plan*/
		getGoalbase().getGoal("llamar_sim_tiempo_superado").drop();

		/* Se actualizan los recursos */
		interaccion.setGrado(content.getInteraccionSocial().getGrado() + Necesidad.NC_NORMAL);
		content.setInteraccionSocial(interaccion);

		energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
		content.setEnergia(energia);

		carisma.setExperiencia(content.getCarisma().getExperiencia() + Habilidad.HB_POCO);
		content.setCarisma(carisma);

		/* Se env a el mensaje de que ha terminado de llamar. Esto se hace a trav s del predicado correspondiente. */
		IMessageEvent inform = createMessageEvent("has_llamado");
		HasLlamado hasLlamado = new HasLlamado(energia, interaccion, carisma);
		inform.setContent(hasLlamado);
		inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
		sendMessage(inform);
		
		/* El tel fono ya no est  ocupado, se actualiza dicha creencia */
		creenciaOcupado.setFact(false);
	}
}
