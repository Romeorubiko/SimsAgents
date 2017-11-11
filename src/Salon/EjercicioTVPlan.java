package Salon;


import java.util.Random;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.EjercicioTV;
import ontologia.conceptos.habilidades.Deporte;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasHechoEjercicioTV;
import ontologia.predicados.TVEstropeadaEjercicio;

public class EjercicioTVPlan extends Plan {
	/**
	 *  Plan body.
	 */
	public void body()
	{
		IMessageEvent	request	= (IMessageEvent)getInitialEvent();
		EjercicioTV content = (EjercicioTV)request.getContent();
		Hambre hambre= content.getHambre();
		Higiene higiene= content.getHigiene();
		Diversion diversion= content.getDiversion();
		Energia energia= content.getEnergia();
		Deporte deporte= content.getDeporte();

		/* Creencias */
		RBeliefbase bb;
		bb=(RBeliefbase) getBeliefbase();
		RBelief creencia1=(RBelief) bb.getBelief("estado_tv");
		Boolean ocupado= (Boolean)creencia1.getFact();
		RBelief creencia2=(RBelief) bb.getBelief("obsolescencia_tv");
		Integer obsolescenciaTV= (Integer)creencia2.getFact();

		if(ocupado){
			IMessageEvent respuesta = createMessageEvent("tv_ocupada");
			sendMessage(respuesta);

		} else{
			creencia1.setFact(true);
			IMessageEvent agree = createMessageEvent("tv_no_ocupada");
			sendMessage(agree);

			obsolescenciaTV= obsolescenciaTV-1;

			if(obsolescenciaTV<=0){
	        	/* Mensaje de failure que se le env�a al Sim */
				IMessageEvent failure = createMessageEvent("tv_estropeada_ejercicio");
				TVEstropeadaEjercicio tvEstropeadaEjercicio= new TVEstropeadaEjercicio(energia,diversion,hambre,higiene,deporte);
				failure.setContent(tvEstropeadaEjercicio);
				sendMessage(failure);
				/*
					Se actualizan las creencias
					tv_ocupada false
					obsolescenciaTV
				*/
				creencia1.setFact(false);
				creencia2.setFact(obsolescenciaTV);
			} else{
	        	/*
		         * Energia
		         */
				energia.setGrado(content.getEnergia().getGrado()- Necesidad.NC_POCO);
				content.setEnergia(energia);

				/*
				 * Diversion
				 */
				diversion.setGrado(content.getDiversion().getGrado()+ Necesidad.NC_NORMAL);
				content.setDiversion(diversion);

				/*
				 * Higiene
				 */
				higiene.setGrado(content.getHigiene().getGrado()- Necesidad.NC_NORMAL);
				content.setHigiene(higiene);

				/*
				 * Hambre
				 */
				hambre.setGrado(content.getHambre().getGrado()- Necesidad.NC_NORMAL);
				content.setHambre(hambre);

				/*
				 * Deporte
				 */
				deporte.setExperiencia(content.getDeporte().getExperiencia()+ Habilidad.HB_POCO);
				content.setDeporte(deporte);

				try {

					wait(Accion.TIEMPO_MEDIO);

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				IMessageEvent inform = createMessageEvent("has_hecho_ejercicio_tv");
				HasHechoEjercicioTV hasHechoEjercicioTV= new HasHechoEjercicioTV(energia,diversion,hambre,higiene,deporte);
				inform.setContent(hasHechoEjercicioTV);
				sendMessage(inform);
				creencia1.setFact(false);

			}

		}



	}
}