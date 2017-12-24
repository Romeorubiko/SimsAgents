package Salon.EquipoMusica;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;

public class RepararEquipoPreguntaPlan extends Plan {
	public RepararEquipoPreguntaPlan() {
	}

	/**
	 * Plan body.
	 */
	public void body() {
		IMessageEvent request = (IMessageEvent) getInitialEvent();

		/* Se toman las creencias necesarias */
		RBeliefbase bb = (RBeliefbase) getBeliefbase();
		RBelief creenciaObsolescencia = (RBelief) bb.getBelief("obsolescencia_");
		Integer obsolescencia = (Integer) creenciaObsolescencia.getFact();
		RBelief creenciaSimReparandoEquipo = (RBelief) bb.getBelief("reparando_equipo");
		Boolean simReparandoEquipo = (Boolean) creenciaSimReparandoEquipo.getFact();

		if (simReparandoEquipo || obsolescencia > 0) {
			/*
			 * Se comprueba si hay un Sim reparando el equipo o no esta roto, en cualquier
			 * caso, se rechaza la peticion de arreglarlo.
			 */
			IMessageEvent refuse = createMessageEvent("peticion_rechazada");
			refuse.setContent(request.getContent());
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(refuse);
		} else {

			/* Se acepta la peticion del sim de arreglar el equipo de musica */
			IMessageEvent agree = createMessageEvent("peticion_aceptada");
			agree.setContent(request.getContent());
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(agree);

			/*
			 * Ahora hay un Sim arreglando el equipo de musica, con lo que se actualiza la
			 * creencia correspondiente.
			 */
			simReparandoEquipo = true;
			creenciaSimReparandoEquipo.setFact(simReparandoEquipo);

			/*
			 * Se guarda el mensaje del sim que esta arreglando la TV asi como el tiempo en
			 * el que terminara de arreglarla mediante creencias.
			 */
			RBelief creenciaTiempoFinRepararTV = (RBelief) bb.getBelief("tiempo_fin_reparar_equipo");
			RBelief creenciaTiempo = (RBelief) bb.getBelief("tiempo_actual");
			RBelief creenciaMensaje = (RBelief) bb.getBelief("mensaje_equipo_roto");

			Integer tiempo = (Integer) creenciaTiempo.getFact();
			creenciaTiempoFinRepararTV.setFact(tiempo + Accion.TIEMPO_CORTO);
			creenciaMensaje.setFact(request);

			IGoal goal = createGoal("reparar_equipo_tiempo_superado");
			dispatchTopLevelGoal(goal);

		}
	}
}