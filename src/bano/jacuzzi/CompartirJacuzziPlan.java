package bano.jacuzzi;

import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.predicados.JacuzziEstropeadoCompartir;

import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;


public class CompartirJacuzziPlan extends Plan {

    public void body(){
    	RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        CompartirJacuzzi content = (CompartirJacuzzi)peticion.getContent();
        
        InteraccionSocial interaccionsocial = content.getInteraccionSocial();
        Energia energia = content.getEnergia();
        
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean estropeado = (Boolean)getBeliefbase().getBelief("estropeado").getFact();
        
        if(ocupado) {
            IMessageEvent respuesta = createMessageEvent("jacuzzi_ocupado");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        else if (estropeado) {
            IMessageEvent respuesta = createMessageEvent("jacuzzi_estropeado");
            JacuzziEstropeadoCompartir response = new JacuzziEstropeadoCompartir();
            
            response.setInteraccionSocial(interaccionsocial);
            response.setEnergia(energia);
            sendMessage(respuesta);
        }
        else if (((Integer) getBeliefbase().getBelief("obsolescencia").getFact()) - 1<=0){
            getBeliefbase().getBelief("estropeado").setFact(Boolean.TRUE);
            IMessageEvent respuesta = createMessageEvent("jacuzzi_estropeado");
            JacuzziEstropeadoCompartir response = new JacuzziEstropeadoCompartir();
            response.setInteraccionSocial(interaccionsocial);
            response.setEnergia(energia);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            int obsolescencia = ((Integer) getBeliefbase().getBelief("obsolescencia").getFact()) - 1;
            getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia);
            int grado_interaccionsocial = content.getInteraccionSocial().getGrado();
            int grado_energia = content.getEnergia().getGrado();
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;

           
            getBeliefbase().getBelief("higiene").setFact(grado_interaccionsocial);
            getBeliefbase().getBelief("energia").setFact(grado_energia);
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(end_timer);

        }
    }
}