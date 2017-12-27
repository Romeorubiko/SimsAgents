package Salon.EquipoMusica;

import java.util.ArrayList;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import ontologia.acciones.EscucharMusica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasEscuchadoMusica;

public class EscucharMusicaRespuestaPlan extends Plan {

	public EscucharMusicaRespuestaPlan() {
	}

	public void body() {
		/*
		 * Se coge el mensaje de la primera posicion del array de mensajes guardado en
		 * la creencia correspondiente
		 */
		RBelief creenciaMensajes = (RBelief) getBeliefbase().getBelief("mensajes_escuchar_musica");
		@SuppressWarnings("unchecked")
		ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) creenciaMensajes.getFact();
		IMessageEvent request = arrayMensajes.get(0);

		/* Se obtienen los recursos del Sim del mensaje */
		EscucharMusica content = (EscucharMusica) request.getContent();
		Energia energia = content.getEnergia();
		Diversion diversion = content.getDiversion();

		/* Se modifican los recursos del Sim */

		energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
		content.setEnergia(energia);

		diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_POCO);
		content.setDiversion(diversion);

		/*
		 * Envio del mensaje al Sim informandole de que ha escuchado musica con exito a
		 * traves del predicado correspondiente
		 */
		IMessageEvent inform = createMessageEvent("has_escuchado_musica");
		HasEscuchadoMusica hasEscuchadoMusica = new HasEscuchadoMusica(energia, diversion);
		inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
		inform.setContent(hasEscuchadoMusica);
		sendMessage(inform);

		/* Actualizacion de las creencias del equipo de musica */
		/*
		 * Puesto que el Sim ha terminado de escuchar musica se elimina la primera
		 * posicion del array de mensajes y la primera posicion del array de tiempos de
		 * finalizacion
		 */
		arrayMensajes.remove(0);
		creenciaMensajes.setFact(arrayMensajes);

		RBelief creenciaTiempo = (RBelief) getBeliefbase().getBelief("tiempos_fin_escuchar_musica");
		@SuppressWarnings("unchecked")
		ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) creenciaTiempo.getFact();
		arrayTiempos.remove(0);
		creenciaTiempo.setValue(arrayTiempos);

		/*
		 * Si el array de tiempos esta vacio, significa que no hay mas sims escuchadno
		 * musica por lo tanto el objetivo correspondiente de desactiva
		 */
		if (arrayTiempos.isEmpty()) {
			getGoalbase().getGoal("escuchar_musica_tiempo_superado").drop();
		}
	}

}
