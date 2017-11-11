package Salon.EquipoMusica;

import java.util.ArrayList;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import ontologia.Accion;
import ontologia.acciones.EscucharMusica;
import ontologia.conceptos.Musica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.predicados.EquipoEstropeadoEscuchandoMusica;

public class EscucharMusicaPreguntaPlan extends Plan {

	public EscucharMusicaPreguntaPlan() {
	}

	public void body() {
		/* Obtención del request que inicia el plan */
		IMessageEvent request = (IMessageEvent) getInitialEvent();
		EscucharMusica content = (EscucharMusica) request.getContent();
		Diversion diversion = content.getDiversion();
		Energia energia = content.getEnergia();

		/* Obtención de las creencias del agente */
		RBelief creenciaMusicaSonando = (RBelief) getBeliefbase().getBelief("musica_sonando"); // El tipo de música que
		Musica musicaSonando = (Musica) creenciaMusicaSonando.getFact();						// está sonando
		RBelief creenciaObsolescencia = (RBelief) getBeliefbase().getBelief("obsolescencia");// Si el equipo está
		int obsolescencia = (int) creenciaObsolescencia.getFact(); 								// estropeado

		/* Al escuchar música siempre se aceptan las peticiones */
		IMessageEvent agree = createMessageEvent("peticion_aceptada");
		agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
		sendMessage(agree);

		/* Se comprueba si el equipo de música está roto */
		if (obsolescencia <= 0) {
			/* La música deja de sonar */
			if (!musicaSonando.equals(null)) {
				creenciaMusicaSonando.setFact(null);
			}
			/* Mensaje de failure enviado con el predicado correspondiente */
			IMessageEvent failure = createMessageEvent("equipo_estropeado_escuchando_mmusica");
			EquipoEstropeadoEscuchandoMusica equipoEstropeado = new EquipoEstropeadoEscuchandoMusica(energia, diversion);
			failure.setContent(equipoEstropeado);
			failure.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(failure);
		} else {
			/*
			 * Se modifican las creencias del agente sobre su obsolescencia.
			 */
			obsolescencia--;
			creenciaObsolescencia.setFact(obsolescencia);

			/*
			 * Se obtienen los arrays que contienen los tiempos y mensajes de los sims que
			 * están a la espera de que se modifiquen sus recursos
			 */

			RBelief creenciaMensajes = (RBelief) getBeliefbase().getBelief("mensajes_escuchar_musica");
			@SuppressWarnings("unchecked")
			ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) creenciaMensajes.getFact();
			RBelief creenciaTiemposFin = (RBelief) getBeliefbase().getBelief("tiempos_escuchar_musica");
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
			arrayTiempos.add(tiempo + Accion.TIEMPO_CORTO);
			creenciaTiemposFin.setFact(arrayTiempos);

			/* Si es el primero en escuchar música se lanza el objetivo */
			if (arrayTiempos.size() == 1) {
				IGoal goal = createGoal("escuchar_musica_tiempo_superado");
				dispatchTopLevelGoal(goal);
			}
		}
	}
}