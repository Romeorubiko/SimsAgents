package Salon.EquipoMusica;

import java.util.ArrayList;

import jadex.adapter.fipa.AgentIdentifier;
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
		AgentIdentifier simParejaBaile = content.getSim();

		/* Se obtienen las creencias necesarias */
		RBelief creenciaSimsBailando = (RBelief) getBeliefbase().getBelief("sims_bailando"); // Numero de sims bailando
		RBelief creenciaMusicaSonando = (RBelief) getBeliefbase().getBelief("musica_sonando"); // El tipo de musica que
																								// esta sonando
		RBelief creenciaObsolescencia = (RBelief) getBeliefbase().getBelief("obsolescencia");// Si el equipo esta
																								// estropeado
		RBelief simsEsperandoPareja = (RBelief) getBeliefbase().getBelief("sims_esperando_pareja");// Los sims que estan
																									// esperando a que
																									// sus parejas pidan
																									// bailar
																									// al equipo de
																									// musica
		RBelief mensajesSimsEsperando = (RBelief) getBeliefbase().getBelief("mensajes_sims_esperando");// Mensajes de los sims que estan esperando a sus parejas

		int simsBailando = (int) creenciaSimsBailando.getFact();
		Musica musicaSonando = (Musica) creenciaMusicaSonando.getFact();
		int obsolescencia = (int) creenciaObsolescencia.getFact();
		@SuppressWarnings("unchecked")
		ArrayList<AgentIdentifier> arraySimsEsperandoParejas = (ArrayList<AgentIdentifier>) simsEsperandoPareja
				.getFact();
		@SuppressWarnings("unchecked")
		ArrayList<IMessageEvent> arrayMensajesSimsEsperando = (ArrayList<IMessageEvent>) mensajesSimsEsperando
				.getFact();

		
		/*
		 * Basandose en sus creencias, el equipo de musica evalua si aceptar la peticion
		 * del Sim
		 */
		AgentIdentifier simPeticion = (AgentIdentifier) request.getParameter(SFipa.SENDER).getValue();
		if (!arraySimsEsperandoParejas.contains(simParejaBaile)) {
			/*
			 * Primero se comprueba si la pareja del sim que ha solicitado bailar esta
			 * esperandole. Si es asi se continua con la ejecucion, si no es asi, se anade
			 * el Sim de la peticion al array de sims que estan esperando a sus parejas de
			 * baile y termina el plan.
			 */
			arraySimsEsperandoParejas.add(simPeticion);
			arrayMensajesSimsEsperando.add(request);

		} else {

			if (simsBailando > 0 && !musicaSonando.equals(musicaPedida)) {
				/*
				 * Se rechaza la peticion si los sims piden bailar una musica que no estan
				 * bailando el resto de Sims
				 */
				IMessageEvent refuse = createMessageEvent("peticion_rechazada");
				refuse.setContent(content);
				refuse.getParameterSet(SFipa.RECEIVERS).addValue(simPeticion);
				sendMessage(refuse);
				
				IMessageEvent refuse2 = createMessageEvent("peticion_rechazada");
				int index= arraySimsEsperandoParejas.indexOf(simParejaBaile);
				refuse2.setContent(arrayMensajesSimsEsperando.get(index));
				refuse2.getParameterSet(SFipa.RECEIVERS).addValue(simParejaBaile);
				sendMessage(refuse2);
				
			} else {
				IMessageEvent agree = createMessageEvent("peticion_aceptada");
				agree.getParameterSet(SFipa.RECEIVERS).addValue(simPeticion);
				agree.setContent(content);
				sendMessage(agree);
				
				IMessageEvent agree2 = createMessageEvent("peticion_aceptada");
				int index= arraySimsEsperandoParejas.indexOf(simParejaBaile);
				agree2.getParameterSet(SFipa.RECEIVERS).addValue(simParejaBaile);
				agree2.setContent(arrayMensajesSimsEsperando.get(index));
				sendMessage(agree2);		
				
				/* El sim que hizo primero la peticion ya no esta esperando, luego se elimina de los arrays de sims esperando y de mensajes */
				arraySimsEsperandoParejas.remove(index);
				IMessageEvent requestPareja = arrayMensajesSimsEsperando.get(index);
				arrayMensajesSimsEsperando.remove(index);

				/* Se comprueba si el equipo de musica esta roto */
				if (obsolescencia <= 0) {
					/* Si habia sims bailando ya no los hay */
					if (simsBailando > 0) {
						creenciaSimsBailando.setFact(0);
					}
					/* La musica deja de sonar */
					if (!musicaSonando.equals(null)) {
						creenciaMusicaSonando.setFact(null);
					}
					/* Mensaje de failure enviado con el predicado correspondiente */
					IMessageEvent failure = createMessageEvent("equipo_estropeado_bailando_sim");
					EquipoEstropeadoBailandoSim equipoEstropeado = new EquipoEstropeadoBailandoSim(interaccion, energia,
							diversion, higiene, hambre, fisico);
					failure.setContent(equipoEstropeado);
					failure.getParameterSet(SFipa.RECEIVERS).addValue(simPeticion);
					sendMessage(failure);
					
					IMessageEvent failure2 = createMessageEvent("equipo_estropeado_bailando_sim");
					BailarConSim content2 = (BailarConSim) requestPareja.getContent();
					interaccion = content2.getInteraccionSocial();
					energia = content2.getEnergia();
					higiene = content2.getHigiene();
					hambre = content2.getHambre();
					diversion = content2.getDiversion();
					fisico = content2.getDeporte();
					equipoEstropeado = new EquipoEstropeadoBailandoSim(interaccion, energia,
							diversion, higiene, hambre, fisico);	
					failure2.setContent(equipoEstropeado);
					failure2.getParameterSet(SFipa.RECEIVERS).addValue(simParejaBaile);
					sendMessage(failure2);
					
				} else {
					/*
					 * Se modifican las creencias del agente sobre su obsolescencia, el numero de
					 * sims bailando y la musica sonando.
					 */
					obsolescencia--;
					creenciaObsolescencia.setFact(obsolescencia);

					simsBailando= simsBailando+2;
					creenciaSimsBailando.setFact(simsBailando);

					creenciaMusicaSonando.setFact(musicaPedida);

					/*
					 * Se obtienen los arrays que contienen los tiempos y mensajes de los sims que
					 * estan a la espera de que se modifiquen sus recursos
					 */

					RBelief creenciaMensajes = (RBelief) getBeliefbase().getBelief("mensajes_bailar_sim");
					@SuppressWarnings("unchecked")
					ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) creenciaMensajes.getFact();
					RBelief creenciaTiemposFin = (RBelief) getBeliefbase().getBelief("tiempos_bailar_sim");
					@SuppressWarnings("unchecked")
					ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) creenciaTiemposFin.getFact();

					/*
					 * Se actualiza el Array de Mensajes anadiendo los mensajes de la pareja de sims bailando
					 */
					arrayMensajes.add(request);
					arrayMensajes.add(requestPareja);
					creenciaMensajes.setFact(arrayMensajes);

					/*
					 * Se actualiza el array de tiempos de finalizacion anadiendo el tiempo de
					 * finalizacion de la accion para el Sim actual a la ultima posicion
					 */
					RBelief creenciaTiempo = (RBelief) getBeliefbase().getBelief("tiempo_actual");
					Integer tiempo = (Integer) creenciaTiempo.getFact();
					arrayTiempos.add(tiempo + Accion.TIEMPO_MEDIO);
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
}
