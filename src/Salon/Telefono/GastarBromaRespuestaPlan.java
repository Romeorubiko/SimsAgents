package Salon.Telefono;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import ontologia.acciones.GastarBromaTel;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasGastadoBroma;
public class GastarBromaRespuestaPlan extends Plan {

	public GastarBromaRespuestaPlan() {
	}
	
	public void body() {
		/* Obtencion de las creencias */
		RBelief creenciaOcupado = (RBelief) getBeliefbase().getBelief("telefono_ocupado");
		RBelief creenciaMensaje = (RBelief) getBeliefbase().getBelief("mensaje");
		RBelief creenciaTiempoBroma = (RBelief) getBeliefbase().getBelief("tiempo_fin_broma");
		
		/* Se recupera el contenido del request guardado en el primer plan a traves de una creencia */
		IMessageEvent request = (IMessageEvent) creenciaMensaje.getFact();
		GastarBromaTel content = (GastarBromaTel) request.getContent();
		Diversion diversion= content.getDiversion();
		Energia energia = content.getEnergia();

		/*
		 * Se actualizan los recursos
		 */
		diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
		content.setDiversion(diversion);

		energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
		content.setEnergia(energia);

		/* Se envia el mensaje de que ha terminado de gastar la broma. Esto se hace a traves del predicado correspondiente. */
		IMessageEvent inform = createMessageEvent("has_gastado_broma");
		HasGastadoBroma hasGastadoBroma = new HasGastadoBroma(energia, diversion);
		inform.setContent(hasGastadoBroma);
		inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
		sendMessage(inform);
		
		/* El telefono ya no esta ocupado, se actualiza dicha creencia */
		creenciaOcupado.setFact(false);
		
		/* La broma ha terminado, por lo que se resetea el valor de la creencia del tiempo de la llamada a 0 */
		creenciaTiempoBroma.setValue(0);
	}
}