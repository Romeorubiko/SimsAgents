package bano.vater;

import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.VaterEstropeado;

import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;


public class UsarVaterPlan extends Plan {

    public void body(){
    	RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        UsarVater content = (UsarVater)peticion.getContent();
        
        Vejiga vejiga = content.getVejiga();
        Energia energia = content.getEnergia();
        
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean estropeado = (Boolean)getBeliefbase().getBelief("estropeado").getFact();
        
        if(ocupado) {
            IMessageEvent respuesta = createMessageEvent("vater_ocupado");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        else if (estropeado) {
            IMessageEvent respuesta = createMessageEvent("vater_estropeado");
            VaterEstropeado response = new VaterEstropeado();
            
            response.setVejiga(vejiga);
            response.setEnergia(energia);
            sendMessage(respuesta);
        }
        else if (((Integer) getBeliefbase().getBelief("obsolescencia").getFact()) - 1 <= 0){
            getBeliefbase().getBelief("estropeado").setFact(Boolean.TRUE);
            IMessageEvent respuesta = createMessageEvent("vater_estropeado");
            VaterEstropeado response = new VaterEstropeado();
            response.setVejiga(vejiga);
            response.setEnergia(energia);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            int obsolescencia = ((Integer) getBeliefbase().getBelief("obsolescencia").getFact()) - 1;
            getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia);
            int grado_vejiga = content.getVejiga().getGrado();
            int grado_energia = content.getEnergia().getGrado();
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;

           
            getBeliefbase().getBelief("vejiga").setFact(grado_vejiga);
            getBeliefbase().getBelief("energia").setFact(grado_energia);
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(end_timer);

        }
    }
}