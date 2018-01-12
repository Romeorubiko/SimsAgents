package bano.vater;

import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.VaterEstropeado;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;


public class UsarVaterPlan extends Plan {

    public void body(){

    	IMessageEvent peticion = (IMessageEvent)getInitialEvent();
        UsarVater content = (UsarVater)peticion.getContent();
        
        Vejiga vejiga = content.getVejiga();
        
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean estropeado = (Boolean)getBeliefbase().getBelief("estropeado").getFact();
        
        if(ocupado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("vater_ocupado");
            respuesta.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        else {
        	getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
        	IMessageEvent agree = createMessageEvent ("vater_no_ocupado");
        	agree.setContent(content);
        	agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
        	sendMessage(agree);
        	if (estropeado.booleanValue()) {
        		IMessageEvent failure = createMessageEvent("vater_estropeado");
                VaterEstropeado response = new VaterEstropeado(vejiga);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
                failure.setContent(response);
                sendMessage(failure);
                getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        	}
        	else if (((Integer) getBeliefbase().getBelief("obsolescencia").getFact()) - 1 <= 0) {
        		getBeliefbase().getBelief("estropeado").setFact(Boolean.TRUE);
                IMessageEvent failure = createMessageEvent("vater_estropeado");
                VaterEstropeado contenido = new VaterEstropeado(vejiga);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
                failure.setContent(contenido);
                sendMessage(failure);
                getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);

        	}
        	else {
        		int obsolescencia = ((Integer) getBeliefbase().getBelief("obsolescencia").getFact()).intValue() - 1;
                getBeliefbase().getBelief("obsolescencia").setFact(new Integer (obsolescencia));
  
                getBeliefbase().getBelief("mensaje_usar_vater").setFact(peticion);
                
                int end_timer = (int) (System.currentTimeMillis()/1000) + (Accion.TIEMPO_MEDIO);
                getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));

              
        	}
        }


    }
}