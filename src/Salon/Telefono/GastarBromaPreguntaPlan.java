package Salon.Telefono;

import java.util.Random;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.GastarBromaTel;
import ontologia.conceptos.habilidades.Carisma;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.predicados.BromaFallida;

public class GastarBromaPreguntaPlan extends Plan {

	public GastarBromaPreguntaPlan() {
	}
	
	public void body() {
		/* Obtencion del request que inicia el plan */
		IMessageEvent request = (IMessageEvent) getInitialEvent();
		GastarBromaTel content = (GastarBromaTel) request.getContent();
		Energia energia = content.getEnergia();
		Diversion diversion = content.getDiversion();
		Carisma carisma = content.getCarisma();

		/* Obtencion de las creencias del agente */
		RBeliefbase bb;
		bb = (RBeliefbase) getBeliefbase();
		RBelief creenciaOcupado = (RBelief) bb.getBelief("telefono_ocupado");
		RBelief creenciaMensaje = (RBelief) bb.getBelief("mensaje");
		RBelief creenciaTiempoFin = (RBelief) bb.getBelief("tiempo_fin_broma");
		Boolean ocupado = (Boolean) creenciaOcupado.getFact();

		/* Si el telefono esta ocupado se rechaza la peticion, si no se acepta */
		if (ocupado) {
			/* Envio del refuse */
			IMessageEvent refuse = createMessageEvent("peticion_rechazada");
			refuse.setContent(content);
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(refuse);
		} else {
			/* Envio del agree */
			IMessageEvent agree = createMessageEvent("peticion_aceptada");
			agree.setContent(content);
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(agree);

			/* Probabilidad de que la broma falle. Dependera del nivel de carisma del Sim. A mas carisma, menor probabilidad de que falle.  */
			Boolean falloBroma;
			if(carisma.getNivel()<4) {
				// Probabilidad del 33%
				falloBroma= new Random().nextInt(3) == 0;
			}
			else if(carisma.getNivel()>=4 && carisma.getNivel()<7) {
				// Probabilidad del 20%
				falloBroma= new Random().nextInt(5) == 0;
			}
			else {
				// Probabilidad del 10%
				falloBroma= new Random().nextInt(10) == 0;
			}

			/*
			 * Si la llamada falla se envia un failure. Si no, pasara a ejecutarse el plan
			 * de GastarBromaRespuestaPlan
			 */
			if (falloBroma) {
				IMessageEvent failure = createMessageEvent("broma_fallida");
				BromaFallida bromaFallida = new BromaFallida(energia, diversion);
				failure.setContent(bromaFallida);
				failure.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
				sendMessage(failure);
			} else {
				/* Se guarda el contenido del request en una creencia para que el segundo plan pueda acceder a dicho contenido */
				creenciaMensaje.setFact(request);
				
				/* Obtencion del tiempo que lleva ejecutandose el agente */
				int tiempo=(int) getBeliefbase().getBelief("tiempo_actual").getFact();
				
				/* Se guarda en una creencia el tiempo en el que se va a terminar la broma*/
				creenciaTiempoFin.setFact(tiempo + Accion.TIEMPO_CORTO);
				
				/* Ahora el telefono esta ocupado, se actualiza la creencia*/
				creenciaOcupado.setFact(true);

			}
		}
	}
}