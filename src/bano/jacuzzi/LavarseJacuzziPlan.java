package bano.jacuzzi;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.JacuzziEstropeadoLavarse;
import ontologia.predicados.TeHasLavado;

import java.util.*;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;



public class LavarseJacuzziPlan extends Plan {

    public void body(){
    	RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Lavarse content = (Lavarse)peticion.getContent();
        
        Higiene higiene = content.getHigiene();
        Energia energia = content.getEnergia();
        
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean estropeado = (Boolean)getBeliefbase().getBelief("estropeado").getFact();
        
        if(ocupado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("jacuzzi_ocupado");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        else if (estropeado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("jacuzzi_estropeado");
            JacuzziEstropeadoLavarse response = new JacuzziEstropeadoLavarse();
            
            response.setHigiene(higiene);
            response.setEnergia(energia);
            sendMessage(respuesta);
        }
        else if (((Integer) getBeliefbase().getBelief("obsolescencia").getFact()).intValue() - 1<=0){
            getBeliefbase().getBelief("estropeado").setFact(Boolean.TRUE);
            IMessageEvent respuesta = createMessageEvent("jacuzzi_estropeado");
            JacuzziEstropeadoLavarse response = new JacuzziEstropeadoLavarse();
            response.setHigiene(higiene);
            response.setEnergia(energia);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            int obsolescencia = ((Integer) getBeliefbase().getBelief("obsolescencia").getFact()).intValue() - 1;
            getBeliefbase().getBelief("obsolescencia").setFact(new Integer (obsolescencia));
            int grado_higiene = content.getHigiene().getGrado();
            int grado_energia = content.getEnergia().getGrado();
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;

           
            getBeliefbase().getBelief("higiene").setFact(new Integer(grado_higiene));
            getBeliefbase().getBelief("energia").setFact(new Integer(grado_energia));
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));

        }
       
}
}