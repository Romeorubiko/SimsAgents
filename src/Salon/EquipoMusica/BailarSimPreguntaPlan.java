package Salon.EquipoMusica;

import java.util.ArrayList;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import ontologia.Accion;
import ontologia.acciones.BailarConSim;
import ontologia.conceptos.Musica;
import ontologia.conceptos.habilidades.Deporte;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.predicados.EquipoEstropeadoBailandoSim;

public class BailarSimPreguntaPlan extends Plan {

	public BailarSimPreguntaPlan() {
	}

	/* Las clases BailarSimPreguntaPlan y BailarSimRespuestaPlan funcionan igual que las de BailarPreguntaPlan y BailarRespuestaPlan
	 * con la diferencia de que en las primeras se modifica el atributo interacción social del Sim. Como se puede observar en el protocolo,
	 * cuando un Sim quiere bailar con otro le envía una petición y si el otro Sim acepta, ambos sims enviarán un request de bailar_con_sim.
	 * Puesto que cada uno envía su request de forma individual, las requests son procesadas por el equipo de música de forma individual y respondidas de forma individual.
	 * Es decir, para el equipo de música es como si estuviera procesando un request de bailar pero modificando el atributo interacción social del Sim */
	
	public void body() {
		/* Se obtiene el contenido del request */
		IMessageEvent request = (IMessageEvent) getInitialEvent();
		BailarConSim content = (BailarConSim) request.getContent();
		Musica musicaPedida = content.getMusica();
		InteraccionSocial interaccion = content.getInteraccionSocial();
		Energia energia = content.getEnergia();
		Higiene higiene = content.getHigiene();
		Hambre hambre = content.getHambre();
		Diversion diversion = content.getDiversion();
		Deporte fisico = content.getDeporte();

		/* Se obtienen las creencias necesarias */
		RBelief creenciaSimsBailando = (RBelief) getBeliefbase().getBelief("sims_bailando"); // Número de sims bailando
		RBelief creenciaMusicaSonando = (RBelief) getBeliefbase().getBelief("musica_sonando"); // El tipo de música que
																								// está sonando
		RBelief creenciaObsolescencia = (RBelief) getBeliefbase().getBelief("obsolescencia");// Si el equipo está
																								// estropeado
		int simsBailando = (int) creenciaSimsBailando.getFact();
		Musica musicaSonando = (Musica) creenciaMusicaSonando.getFact();
		int obsolescencia = (int) creenciaObsolescencia.getFact();

		/*
		 * Basándose en sus creencias, el equipo de música evalúa si aceptar la petición
		 * del Sim
		 */
		if (simsBailando > 0 && !musicaSonando.equals(musicaPedida)) {
			/*
			 * Se rechaza la petición si el Sim pide bailar una música que no están bailando
			 * el resto de Sims
			 */
			IMessageEvent refuse = createMessageEvent("peticion_rechazada");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(refuse);
		} else {
			IMessageEvent agree = createMessageEvent("peticion_aceptada");
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(agree);

			/* Se comprueba si el equipo de música está roto */
			if (obsolescencia <= 0) {
				/* Si había sims bailando ya no los hay */
				if (simsBailando > 0) {
					creenciaSimsBailando.setFact(0);
				}
				/* La música deja de sonar */
				if (!musicaSonando.equals(null)) {
					creenciaMusicaSonando.setFact(null);
				}
				/* Mensaje de failure enviado con el predicado correspondiente */
				IMessageEvent failure = createMessageEvent("equipo_estropeado_bailando_sim");
				EquipoEstropeadoBailandoSim equipoEstropeado = new EquipoEstropeadoBailandoSim(interaccion, energia,
						diversion, higiene, hambre, fisico);
				failure.setContent(equipoEstropeado);
				failure.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
				sendMessage(failure);
			} else {
				/*
				 * Se modifican las creencias del agente sobre su obsolescencia, el número de
				 * sims bailando y la música sonando.
				 */
				obsolescencia--;
				creenciaObsolescencia.setFact(obsolescencia);

				simsBailando++;
				creenciaSimsBailando.setFact(simsBailando);

				creenciaMusicaSonando.setFact(musicaPedida);

				/*
				 * Se obtienen los arrays que contienen los tiempos y mensajes de los sims que
				 * están a la espera de que se modifiquen sus recursos
				 */

				RBelief creenciaMensajes = (RBelief) getBeliefbase().getBelief("mensajes_bailar_sim");
				@SuppressWarnings("unchecked")
				ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) creenciaMensajes.getFact();
				RBelief creenciaTiemposFin = (RBelief) getBeliefbase().getBelief("tiempos_bailar_sim");
				@SuppressWarnings("unchecked")
				ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) creenciaTiemposFin.getFact();

				/*
				 * Se actualiza el Array de Mensajes añadiendo el mensaje del sim actual en la
				 * última posicion
				 */
				arrayMensajes.add(request);
				creenciaMensajes.setFact(arrayMensajes);

				/*
				 * Se actualiza el array de tiempos de finalización añadiendo el tiempo de
				 * finalización de la acción para el Sim actual a la última posición
				 */
				RBelief creenciaTiempo = (RBelief) getBeliefbase().getBelief("tiempo_actual");
				Integer tiempo = (Integer) creenciaTiempo.getFact();
				arrayTiempos.add(tiempo + Accion.TIEMPO_MEDIO);
				creenciaTiemposFin.setFact(arrayTiempos);

				/* Si es el primero en bailar con un sim se lanza el objetivo */
				if (arrayTiempos.size() == 1) {
					IGoal goal = createGoal("bailar_sim_tiempo_superado");
					dispatchTopLevelGoal(goal);
				}

			}
		}

	}
}
