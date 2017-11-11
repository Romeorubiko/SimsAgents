package Salon.EquipoMusica;

import java.util.ArrayList;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import ontologia.acciones.BailarConSim;
import ontologia.conceptos.Musica;
import ontologia.conceptos.Musica.TiposMusica;
import ontologia.conceptos.habilidades.Deporte;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasBailadoSim;

public class BailarSimRespuestaPlan extends Plan {

	public BailarSimRespuestaPlan() {
	}

	public void body() {
		/*
		 * Se coge el mensaje de la primera posicion del array de mensajes guardado en
		 * la creencia correspondiente
		 */
		RBelief creenciaMensajes = (RBelief) getBeliefbase().getBelief("mensajes_bailar_sim");
		@SuppressWarnings("unchecked")
		ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) creenciaMensajes.getFact();
		IMessageEvent request = arrayMensajes.get(0);

		/* Se obtienen los recursos del Sim del mensaje */
		BailarConSim content = (BailarConSim) request.getContent();
		Musica musicaPedida = content.getMusica();
		InteraccionSocial interaccion = content.getInteraccionSocial();
		Energia energia = content.getEnergia();
		Higiene higiene = content.getHigiene();
		Hambre hambre = content.getHambre();
		Diversion diversion = content.getDiversion();
		Deporte fisico = content.getDeporte();

		/* Se modifican los recursos del Sim dependiendo del tipo de música que baile */
		if (musicaPedida.getTipo().equals(TiposMusica.ROCK)) {
			energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_MUCHO);
			content.setEnergia(energia);

			higiene.setGrado(content.getHigiene().getGrado() - Necesidad.NC_MUCHO);
			content.setHigiene(higiene);

			hambre.setGrado(content.getHambre().getGrado() - Necesidad.NC_MUCHO);
			content.setHambre(hambre);

			diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_MUCHO);
			content.setDiversion(diversion);

			fisico.setExperiencia(content.getDeporte().getExperiencia() + Habilidad.HB_MUCHO);
			content.setDeporte(fisico);

		} else if (musicaPedida.getTipo().equals(TiposMusica.POP)) {
			energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_NORMAL);
			content.setEnergia(energia);

			higiene.setGrado(content.getHigiene().getGrado() - Necesidad.NC_NORMAL);
			content.setHigiene(higiene);

			hambre.setGrado(content.getHambre().getGrado() - Necesidad.NC_NORMAL);
			content.setHambre(hambre);

			diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
			content.setDiversion(diversion);

			fisico.setExperiencia(content.getDeporte().getExperiencia() + Habilidad.HB_NORMAL);
			content.setDeporte(fisico);
		} else {

			energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
			content.setEnergia(energia);

			higiene.setGrado(content.getHigiene().getGrado() - Necesidad.NC_POCO);
			content.setHigiene(higiene);

			hambre.setGrado(content.getHambre().getGrado() - Necesidad.NC_POCO);
			content.setHambre(hambre);

			diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_POCO);
			content.setDiversion(diversion);

			fisico.setExperiencia(content.getDeporte().getExperiencia() + Habilidad.HB_POCO);
			content.setDeporte(fisico);
		}

		interaccion.setGrado(content.getInteraccionSocial().getGrado() + Necesidad.NC_POCO);
		content.setInteraccionSocial(interaccion);

		/*
		 * Envío del mensaje al Sim informándole de que ha bailado con éxito a través
		 * del predicado correspondiente
		 */
		IMessageEvent inform = createMessageEvent("has_bailado_sim");
		HasBailadoSim hasBailadoSim = new HasBailadoSim(interaccion, energia, higiene, diversion, hambre, fisico);
		inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
		inform.setContent(hasBailadoSim);
		sendMessage(inform);

		/* Actualización de las creencias del equipo de música */
		/*
		 * El sim ha dejado de bailar, con lo que el número de sims bailando disminuye
		 */
		RBelief creenciaSimsBailando = (RBelief) getBeliefbase().getBelief("sims_bailando");
		int simsBailando = (int) creenciaSimsBailando.getFact();
		simsBailando--;
		creenciaSimsBailando.setFact(simsBailando);

		/*
		 * Puesto que el Sim ha terminado de bailar se elimina la primera posición del
		 * array de mensajes y la primera posición del array de tiempos de finalización
		 */
		arrayMensajes.remove(0);
		creenciaMensajes.setFact(arrayMensajes);

		RBelief creenciaTiempo = (RBelief) getBeliefbase().getBelief("tiempos_fin_bailar_sim");
		@SuppressWarnings("unchecked")
		ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) creenciaTiempo.getFact();
		arrayTiempos.remove(0);
		creenciaTiempo.setValue(arrayTiempos);

		/*
		 * Si el array de tiempos está vacío, significa que no hay más sims bailando
		 * acompañados (puede haberlos bailando solos) por lo tanto el objetivo
		 * correspondiente de desactiva
		 */
		if (arrayTiempos.isEmpty()) {
			getGoalbase().getGoal("bailar_sim_tiempo_superado").drop();
		}
	}

}
