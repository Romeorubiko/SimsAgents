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
	 * con la diferencia de que en las primeras se modifica el atributo interacci n social del Sim. Como se puede observar en el protocolo,
	 * cuando un Sim quiere bailar con otro le env a una petici n y si el otro Sim acepta, ambos sims enviar n un request de bailar_con_sim.
	 * Puesto que cada uno env a su request de forma individual, las requests son procesadas por el equipo de m sica de forma individual y respondidas de forma individual.
	 * Es decir, para el equipo de m sica es como si estuviera procesando un request de bailar pero modificando el atributo interacci n social del Sim */
	
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
		RBelief creenciaSimsBailando = (RBelief) getBeliefbase().getBelief("sims_bailando"); // N mero de sims bailando
		RBelief creenciaMusicaSonando = (RBelief) getBeliefbase().getBelief("musica_sonando"); // El tipo de m sica que
																								// est  sonando
		RBelief creenciaObsolescencia = (RBelief) getBeliefbase().getBelief("obsolescencia");// Si el equipo est 
																								// estropeado
		int simsBailando = (int) creenciaSimsBailando.getFact();
		Musica musicaSonando = (Musica) creenciaMusicaSonando.getFact();
		int obsolescencia = (int) creenciaObsolescencia.getFact();

		/*
		 * Bas ndose en sus creencias, el equipo de m sica eval a si aceptar la petici n
		 * del Sim
		 */
		if (simsBailando > 0 && !musicaSonando.equals(musicaPedida)) {
			/*
			 * Se rechaza la petici n si el Sim pide bailar una m sica que no est n bailando
			 * el resto de Sims
			 */
			IMessageEvent refuse = createMessageEvent("peticion_rechazada");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(refuse);
		} else {
			IMessageEvent agree = createMessageEvent("peticion_aceptada");
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(agree);

			/* Se comprueba si el equipo de m sica est  roto */
			if (obsolescencia <= 0) {
				/* Si hab a sims bailando ya no los hay */
				if (simsBailando > 0) {
					creenciaSimsBailando.setFact(0);
				}
				/* La m sica deja de sonar */
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
				 * Se modifican las creencias del agente sobre su obsolescencia, el n mero de
				 * sims bailando y la m sica sonando.
				 */
				obsolescencia--;
				creenciaObsolescencia.setFact(obsolescencia);

				simsBailando++;
				creenciaSimsBailando.setFact(simsBailando);

				creenciaMusicaSonando.setFact(musicaPedida);

				/*
				 * Se obtienen los arrays que contienen los tiempos y mensajes de los sims que
				 * est n a la espera de que se modifiquen sus recursos
				 */

				RBelief creenciaMensajes = (RBelief) getBeliefbase().getBelief("mensajes_bailar_sim");
				@SuppressWarnings("unchecked")
				ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) creenciaMensajes.getFact();
				RBelief creenciaTiemposFin = (RBelief) getBeliefbase().getBelief("tiempos_bailar_sim");
				@SuppressWarnings("unchecked")
				ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) creenciaTiemposFin.getFact();

				/*
				 * Se actualiza el Array de Mensajes a adiendo el mensaje del sim actual en la
				 *  ltima posicion
				 */
				arrayMensajes.add(request);
				creenciaMensajes.setFact(arrayMensajes);

				/*
				 * Se actualiza el array de tiempos de finalizaci n a adiendo el tiempo de
				 * finalizaci n de la acci n para el Sim actual a la  ltima posici n
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
