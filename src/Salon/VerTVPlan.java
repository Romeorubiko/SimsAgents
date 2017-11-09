package Salon;

import java.util.Random;

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
		RBeliefbase bb;
		bb=(RBeliefbase) getBeliefbase();
		RBelief creencia1=(RBelief) bb.getBelief("estado_tv");
		Boolean ocupado= (Boolean)creencia1.getFact();
		RBelief creencia2=(RBelief) bb.getBelief("obsolescencia_tv");
		Integer obsolescenciaTV= (Integer)creencia2.getFact();
				
		if(ocupado){
			IMessageEvent respuesta = createMessageEvent("tv_ocupada");
	        sendMessage(respuesta);	        
		}else{
			creencia1.setFact(true);
			IMessageEvent agree = createMessageEvent("tv_no_ocupada");
	        sendMessage(agree);

	        obsolescenciaTV= obsolescenciaTV-1;

	        if(obsolescenciaTV<=0){
	        	/* Mensaje de failure que se le env�a al Sim */
				IMessageEvent failure = createMessageEvent("tv_estropeada");
				TVEstropeada tvEstropeada= new TVEstropeada(canal,energia,diversion);
				failure.setContent(tvEstropeada);
				sendMessage(failure);
				/* Se actualizan las creencias
				   tv_ocupada false
				 * obsolescenciaTV*/
				creencia1.setFact(false);
				creencia2.setFact(obsolescenciaTV);
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
				
				try {
			
					wait(Accion.TIEMPO_MEDIO);
		            
		        } catch (InterruptedException e1) {
		            e1.printStackTrace();
		        }
				
				IMessageEvent inform = createMessageEvent("has_visto_tv");
				HasVistoTV hasvistoTV= new HasVistoTV(canal,energia,diversion);
		        inform.setContent(hasvistoTV);
				sendMessage(inform);
				creencia1.setFact(false);
	        }
		}
	}
}
