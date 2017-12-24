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
		 * Se coge el mensaje de las dos primeras posiciones del array de mensajes guardado en
		 * la creencia correspondiente.
		 */
		RBelief creenciaMensajes = (RBelief) getBeliefbase().getBelief("mensajes_bailar_sim");
		@SuppressWarnings("unchecked")
		ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) creenciaMensajes.getFact();
		IMessageEvent request1 = arrayMensajes.get(0);
		IMessageEvent request2 = arrayMensajes.get(1);

		/* Se obtienen los recursos de los sims de ambos mensajes */		
		BailarConSim content1 = (BailarConSim) request1.getContent();
		Musica musicaPedida = content1.getMusica();
		InteraccionSocial interaccion1 = content1.getInteraccionSocial();
		Energia energia1 = content1.getEnergia();
		Higiene higiene1 = content1.getHigiene();
		Hambre hambre1 = content1.getHambre();
		Diversion diversion1 = content1.getDiversion();
		Deporte fisico1 = content1.getDeporte();
		
		BailarConSim content2 = (BailarConSim) request2.getContent();
		InteraccionSocial interaccion2 = content2.getInteraccionSocial();
		Energia energia2 = content2.getEnergia();
		Higiene higiene2 = content2.getHigiene();
		Hambre hambre2 = content2.getHambre();
		Diversion diversion2 = content2.getDiversion();
		Deporte fisico2 = content2.getDeporte();

		/* Se modifican los recursos de ambos sims dependiendo del tipo de musica que hayan bailado */
		if (musicaPedida.getTipo().equals(TiposMusica.ROCK)) {
			energia1.setGrado(content1.getEnergia().getGrado() - Necesidad.NC_MUCHO);
			content1.setEnergia(energia1);
			energia2.setGrado(content2.getEnergia().getGrado() - Necesidad.NC_MUCHO);
			content2.setEnergia(energia2);

			higiene1.setGrado(content1.getHigiene().getGrado() - Necesidad.NC_MUCHO);
			content1.setHigiene(higiene1);
			higiene2.setGrado(content2.getHigiene().getGrado() - Necesidad.NC_MUCHO);
			content2.setHigiene(higiene2);

			hambre1.setGrado(content1.getHambre().getGrado() - Necesidad.NC_MUCHO);
			content1.setHambre(hambre1);
			hambre2.setGrado(content2.getHambre().getGrado() - Necesidad.NC_MUCHO);
			content2.setHambre(hambre2);

			diversion1.setGrado(content1.getDiversion().getGrado() + Necesidad.NC_MUCHO);
			content1.setDiversion(diversion1);
			diversion2.setGrado(content2.getDiversion().getGrado() + Necesidad.NC_MUCHO);
			content2.setDiversion(diversion2);

			fisico1.setExperiencia(content1.getDeporte().getExperiencia() + Habilidad.HB_MUCHO);
			content1.setDeporte(fisico1);
			fisico2.setExperiencia(content2.getDeporte().getExperiencia() + Habilidad.HB_MUCHO);
			content2.setDeporte(fisico2);

		} else if (musicaPedida.getTipo().equals(TiposMusica.POP)) {
			energia1.setGrado(content1.getEnergia().getGrado() - Necesidad.NC_NORMAL);
			content1.setEnergia(energia1);
			energia2.setGrado(content2.getEnergia().getGrado() - Necesidad.NC_NORMAL);
			content2.setEnergia(energia2);

			higiene1.setGrado(content1.getHigiene().getGrado() - Necesidad.NC_NORMAL);
			content1.setHigiene(higiene1);
			higiene2.setGrado(content2.getHigiene().getGrado() - Necesidad.NC_NORMAL);
			content2.setHigiene(higiene2);

			hambre1.setGrado(content1.getHambre().getGrado() - Necesidad.NC_NORMAL);
			content1.setHambre(hambre1);
			hambre2.setGrado(content2.getHambre().getGrado() - Necesidad.NC_NORMAL);
			content2.setHambre(hambre2);

			diversion1.setGrado(content1.getDiversion().getGrado() + Necesidad.NC_NORMAL);
			content1.setDiversion(diversion1);
			diversion2.setGrado(content2.getDiversion().getGrado() + Necesidad.NC_NORMAL);
			content2.setDiversion(diversion2);

			fisico1.setExperiencia(content1.getDeporte().getExperiencia() + Habilidad.HB_NORMAL);
			content1.setDeporte(fisico1);
			fisico2.setExperiencia(content2.getDeporte().getExperiencia() + Habilidad.HB_NORMAL);
			content2.setDeporte(fisico2);
		} else {
			energia1.setGrado(content1.getEnergia().getGrado() - Necesidad.NC_POCO);
			content1.setEnergia(energia1);
			energia2.setGrado(content2.getEnergia().getGrado() - Necesidad.NC_POCO);
			content2.setEnergia(energia2);

			higiene1.setGrado(content1.getHigiene().getGrado() - Necesidad.NC_POCO);
			content1.setHigiene(higiene1);
			higiene2.setGrado(content2.getHigiene().getGrado() - Necesidad.NC_POCO);
			content2.setHigiene(higiene2);

			hambre1.setGrado(content1.getHambre().getGrado() - Necesidad.NC_POCO);
			content1.setHambre(hambre1);
			hambre2.setGrado(content2.getHambre().getGrado() - Necesidad.NC_POCO);
			content2.setHambre(hambre2);

			diversion1.setGrado(content1.getDiversion().getGrado() + Necesidad.NC_POCO);
			content1.setDiversion(diversion1);
			diversion2.setGrado(content2.getDiversion().getGrado() + Necesidad.NC_POCO);
			content2.setDiversion(diversion2);

			fisico1.setExperiencia(content1.getDeporte().getExperiencia() + Habilidad.HB_POCO);
			content1.setDeporte(fisico1);
			fisico2.setExperiencia(content2.getDeporte().getExperiencia() + Habilidad.HB_POCO);
			content2.setDeporte(fisico2);
		}

		interaccion1.setGrado(content1.getInteraccionSocial().getGrado() + Necesidad.NC_POCO);
		content1.setInteraccionSocial(interaccion1);
		interaccion2.setGrado(content2.getInteraccionSocial().getGrado() + Necesidad.NC_POCO);
		content2.setInteraccionSocial(interaccion2);

		/*
		 * Envio del mensaje a los sims informandoles de que han bailado juntos con exito a traves
		 * del predicado correspondiente
		 */
		IMessageEvent inform1 = createMessageEvent("has_bailado_sim");
		IMessageEvent inform2 = createMessageEvent("has_bailado_sim");
		HasBailadoSim hasBailadoSim1 = new HasBailadoSim(interaccion1, energia1, higiene1, diversion1, hambre1, fisico1);
		HasBailadoSim hasBailadoSim2 = new HasBailadoSim(interaccion2, energia2, higiene2, diversion2, hambre2, fisico2);
		inform1.getParameterSet(SFipa.RECEIVERS).addValue(request1.getParameterSet(SFipa.SENDER).getValues());
		inform2.getParameterSet(SFipa.RECEIVERS).addValue(request2.getParameterSet(SFipa.SENDER).getValues());
		inform1.setContent(hasBailadoSim1);
		inform2.setContent(hasBailadoSim2);
		sendMessage(inform1);
		sendMessage(inform2);
		
		/* Actualizacion de las creencias del equipo de musica */
		/*
		 *Los Sims han dejado de bailar, con lo que el numero de sims bailando disminuye
		 */
		RBelief creenciaSimsBailando = (RBelief) getBeliefbase().getBelief("sims_bailando");
		int simsBailando = (int) creenciaSimsBailando.getFact();
		simsBailando= simsBailando - 2;
		creenciaSimsBailando.setFact(simsBailando);

		/*
		 * Puesto que los Sims han terminado de bailar se elimina la primera y segunda posicion del
		 * array de mensajes y la primera y segunda posicion del array de tiempos de finalizacion
		 */
		arrayMensajes.remove(0);
		arrayMensajes.remove(0);
		creenciaMensajes.setFact(arrayMensajes);

		RBelief creenciaTiempo = (RBelief) getBeliefbase().getBelief("tiempos_fin_bailar_sim");
		@SuppressWarnings("unchecked")
		ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) creenciaTiempo.getFact();
		arrayTiempos.remove(0);
		arrayTiempos.remove(0);
		creenciaTiempo.setValue(arrayTiempos);

		/*
		 * Si el array de tiempos esta vacio, significa que no hay mas sims bailando
		 * acompanados (puede haberlos bailando solos) por lo tanto el objetivo
		 * correspondiente de desactiva
		 */
		if (arrayTiempos.isEmpty()) {
			getGoalbase().getGoal("bailar_sim_tiempo_superado").drop();
		}
	}

}
