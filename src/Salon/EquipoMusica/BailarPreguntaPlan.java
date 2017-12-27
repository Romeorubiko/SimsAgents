package Salon.EquipoMusica;

import java.util.ArrayList;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import ontologia.Accion;
import ontologia.acciones.Bailar;
import ontologia.conceptos.Musica;
import ontologia.conceptos.habilidades.Deporte;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.predicados.EquipoEstropeadoBailando;

public class BailarPreguntaPlan extends Plan {

	public BailarPreguntaPlan() {
	}

	public void body() {
		/* Se obtiene el contenido del request */
		IMessageEvent request = (IMessageEvent) getInitialEvent();
		Bailar content = (Bailar) request.getContent();
		Musica musicaPedida = content.getMusica();
		Energia energia = content.getEnergia();
		Higiene higiene = content.getHigiene();
		Hambre hambre = content.getHambre();
		Diversion diversion = content.getDiversion();
		Deporte fisico = content.getDeporte();

		/* Se obtienen las creencias necesarias */
		RBelief creenciaSimsBailando = (RBelief) getBeliefbase().getBelief("sims_bailando"); // Numero de sims bailando
		RBelief creenciaMusicaSonando = (RBelief) getBeliefbase().getBelief("musica_sonando"); // El tipo de musica que
																								// esta sonando
		RBelief creenciaObsolescencia = (RBelief) getBeliefbase().getBelief("obsolescencia");// Si el equipo esta
																								// estropeado
		int simsBailando = (int) creenciaSimsBailando.getFact();
		Musica musicaSonando = (Musica) creenciaMusicaSonando.getFact();
		int obsolescencia = (int) creenciaObsolescencia.getFact();

		/*
		 * Basandose en sus creencias, el equipo de musica evalua si aceptar la peticion
		 * del Sim
		 */
		if (simsBailando > 0 && !musicaSonando.equals(musicaPedida)) {
			/*
			 * Se rechaza la peticion si el Sim pide bailar una musica que no estan bailando
			 * el resto de Sims
			 */
			IMessageEvent refuse = createMessageEvent("peticion_rechazada");
			refuse.setContent(content);
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(refuse);
		} else {
			IMessageEvent agree = createMessageEvent("peticion_aceptada");
			agree.setContent(content);
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(agree);

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
				IMessageEvent failure = createMessageEvent("equipo_estropeado_bailando");
				EquipoEstropeadoBailando equipoEstropeado = new EquipoEstropeadoBailando(energia, diversion, higiene,
						hambre, fisico);
				failure.setContent(equipoEstropeado);
				failure.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
				sendMessage(failure);
			} else {
				/*
				 * Se modifican las creencias del agente sobre su obsolescencia, el numero de
				 * sims bailando y la musica sonando.
				 */
				obsolescencia--;
				creenciaObsolescencia.setFact(obsolescencia);

				simsBailando++;
				creenciaSimsBailando.setFact(simsBailando);

				// Se actualiza la creencia por si no estaba sonando la musica.
				creenciaMusicaSonando.setFact(musicaPedida);

				/*
				 * Se obtienen los arrays que contienen los tiempos y mensajes de los sims que
				 * estan a la espera de que se modifiquen sus recursos
				 */

				RBelief creenciaMensajes = (RBelief) getBeliefbase().getBelief("mensajes_bailar");
				@SuppressWarnings("unchecked")
				ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) creenciaMensajes.getFact();
				RBelief creenciaTiemposFin = (RBelief) getBeliefbase().getBelief("tiempos_bailar");
				@SuppressWarnings("unchecked")
				ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) creenciaTiemposFin.getFact();

				/*
				 * Se actualiza el Array de Mensajes anadiendo el mensaje del sim actual en la
				 * ultima posicion
				 */
				arrayMensajes.add(request);
				creenciaMensajes.setFact(arrayMensajes);

				/*
				 * Se actualiza el array de tiempos de finalizacion anadiendo el tiempo de
				 * finalizacion de la accion para el Sim actual a la ultima posicion
				 */
				RBelief creenciaTiempo = (RBelief) getBeliefbase().getBelief("tiempo_actual");
				Integer tiempo = (Integer) creenciaTiempo.getFact();
				arrayTiempos.add(tiempo + Accion.TIEMPO_MEDIO);
				creenciaTiemposFin.setFact(arrayTiempos);

				/* Si es el primero en bailar solo se lanza el objetivo */
				if (arrayTiempos.size() == 1) {
					IGoal goal = createGoal("bailar_tiempo_superado");
					dispatchTopLevelGoal(goal);
				}

			}
		}

	}
}
