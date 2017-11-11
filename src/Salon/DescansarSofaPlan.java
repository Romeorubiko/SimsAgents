package Salon;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.Descansar;
import ontologia.conceptos.Descanso;
import ontologia.conceptos.Descanso.tipoDescanso;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasDescansado;

/**
 *  Plan to handle join request of a player.
 */
public class DescansarSofaPlan extends Plan
{
	/**
	 *  Plan body.
	 */
	public void body()
	{
		IMessageEvent	request	= (IMessageEvent)getInitialEvent();
		Descansar content = (Descansar)request.getContent();
		Descanso tipo= content.getTipo();
		Energia energia= content.getEnergia();
		
		/* Creencias */ 
		RBeliefbase bb=(RBeliefbase) getBeliefbase();
		RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_sofa");
		Boolean ocupado= (Boolean)creenciaOcupado.getFact();
				
		if(ocupado){
			IMessageEvent refuse = createMessageEvent("sofa_ocupado");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
	        sendMessage(refuse);
		}else{
			creenciaOcupado.setFact(true);
			IMessageEvent agree = createMessageEvent("sofa_no_ocupado");
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
	        sendMessage(agree);
	       
			if(tipo.getTipoDescanso().equals(tipoDescanso.DORMIR)){
				energia.setGrado(content.getEnergia().getGrado()+Necesidad.NC_MUCHO);
				content.setEnergia(energia);
			}else{
				energia.setGrado(content.getEnergia().getGrado()+Necesidad.NC_NORMAL);
				content.setEnergia(energia);
			}
			
			try {
				if(tipo.getTipoDescanso().equals(tipoDescanso.DORMIR)){
					wait(Accion.TIEMPO_LARGO);
				}else{
					wait(Accion.TIEMPO_MEDIO);
				}
	            
	        } catch (InterruptedException e1) {
	            e1.printStackTrace();
	        }
			
			IMessageEvent inform = createMessageEvent("has_descansado");
			HasDescansado hasDescansado= new HasDescansado(energia);
	        inform.setContent(hasDescansado);
			inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
	        sendMessage(inform);
			creenciaOcupado.setFact(false);
		}
		
		
		
	}
	
}
