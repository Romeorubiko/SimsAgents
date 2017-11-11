package Salon;

import java.util.Random;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.VerTV;
import ontologia.conceptos.CanalTV;
import ontologia.conceptos.CanalTV.Canales;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasVistoTV;
import ontologia.predicados.TVEstropeada;

public class VerTVPlan extends Plan{
	/**
	 *  Plan body.
	 */
	public void body()
	{
		IMessageEvent	request	= (IMessageEvent)getInitialEvent();
		VerTV content = (VerTV)request.getContent();
		CanalTV canal= content.getCanalTV();
		Diversion diversion= content.getDiversion();
		Energia energia= content.getEnergia();
		
		/* Creencias */ 
		RBeliefbase bb=(RBeliefbase) getBeliefbase();
		RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupada_tv");
		Boolean ocupado= (Boolean)creenciaOcupado.getFact();
		RBelief creenciaObsolescencia=(RBelief) bb.getBelief("obsolescencia_tv");
		Integer obsolescenciaTV= (Integer)creenciaObsolescencia.getFact();
				
		if(ocupado){
			IMessageEvent refuse = createMessageEvent("tv_ocupada");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
	        sendMessage(refuse);
		}else{
			creenciaOcupado.setFact(true);
			IMessageEvent agree = createMessageEvent("tv_no_ocupada");
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(agree);

	        obsolescenciaTV= obsolescenciaTV-1;

	        if(obsolescenciaTV<=0){
	        	/* Mensaje de failure que se le env�a al Sim */
				IMessageEvent failure = createMessageEvent("tv_estropeada");
				TVEstropeada tvEstropeada= new TVEstropeada(canal,energia,diversion);
				failure.setContent(tvEstropeada);
				failure.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
				sendMessage(failure);

				creenciaObsolescencia.setFact(obsolescenciaTV);
				creenciaOcupado.setFact(false);
	        }else{
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
				 * Se modifican las habilidades Carisma o Cocina dependiendo del canal que solicie el Sim
				 */
				if(canal.getCanal().equals(Canales.COCINA)){
					canal.getCocina().setExperiencia(content.getCanalTV().getCocina().getExperiencia()+Habilidad.HB_NORMAL);
					content.getCanalTV().setCocina(canal.getCocina());
				} else{
					canal.getCarisma().setExperiencia(content.getCanalTV().getCarisma().getExperiencia()+Habilidad.HB_NORMAL);
					content.getCanalTV().setCarisma(canal.getCarisma());
				}
				

				
				IMessageEvent inform = createMessageEvent("has_visto_tv");
				HasVistoTV hasvistoTV= new HasVistoTV(canal,energia,diversion);
		        inform.setContent(hasvistoTV);
				inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
				sendMessage(inform);
				creenciaOcupado.setFact(false);
	        }
		}
	}
}
