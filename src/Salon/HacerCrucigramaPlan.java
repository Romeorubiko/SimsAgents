package Salon;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.HacerCrucigrama;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Logica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasHechoCrucigrama;

public class HacerCrucigramaPlan extends Plan{

	/**
	 *  Plan body.
	 */
	public void body()
	{
		IMessageEvent	request	= (IMessageEvent)getInitialEvent();
		HacerCrucigrama content = (HacerCrucigrama)request.getContent();
		Diversion diversion= content.getDiversion();
		Energia energia= content.getEnergia();
		Logica logica= content.getLogica();
		
		/* Creencias */ 
		RBeliefbase bb;
		bb=(RBeliefbase) getBeliefbase();
		RBelief creencia1=(RBelief) bb.getBelief("estado_periodico");
		Boolean ocupado= (Boolean)creencia1.getFact();
				
		if(ocupado){
			IMessageEvent respuesta = createMessageEvent("periodico_ocupado");
	        sendMessage(respuesta);	        
		}else{
			creencia1.setFact(true);
			IMessageEvent agree = createMessageEvent("periodico_no_ocupado");
	        sendMessage(agree);
	        /*
	         * Se actualiza el grado de diversión 
	         */
			diversion.setGrado(content.getDiversion().getGrado()+Necesidad.NC_NORMAL);
			content.setDiversion(diversion);
			
			/*
	         * Se actualiza el grado de energía
	         */
			energia.setGrado(content.getEnergia().getGrado()- Necesidad.NC_POCO);
			content.setEnergia(energia);
			
			/*
	         * Se actualiza la experiencia en lógica
	         */
			logica.setExperiencia(content.getLogica().getExperiencia()+Habilidad.HB_NORMAL);
			content.setLogica(logica);
				
			try {
				wait(Accion.TIEMPO_CORTO);
			} catch (InterruptedException e1) {
	            e1.printStackTrace();
	        }
				
			IMessageEvent inform = createMessageEvent("has_hecho_crucigrama");
			HasHechoCrucigrama hasHechoCrucigrama= new HasHechoCrucigrama(energia,diversion,logica);
	        inform.setContent(hasHechoCrucigrama);
			sendMessage(inform);
			creencia1.setFact(false);
		}
	}
}
