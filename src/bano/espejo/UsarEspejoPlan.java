package bano.espejo;

import ontologia.Accion;
import ontologia.acciones.*;


import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;


public class UsarEspejoPlan extends Plan {

    public void body(){
    	RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        UsarEspejo content = (UsarEspejo)peticion.getContent();
        
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        
        if(ocupado) {
            IMessageEvent respuesta = createMessageEvent("espejo_ocupado");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            int experiencia_carisma = content.getCarisma().getExperiencia();
            int nivel_carisma = content.getCarisma().getNivel();      
            int grado_energia = content.getEnergia().getGrado();
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;

           
            getBeliefbase().getBelief("experiencia_carisma").setFact(experiencia_carisma);
            getBeliefbase().getBelief("nivel_carisma").setFact(nivel_carisma);
            getBeliefbase().getBelief("energia").setFact(grado_energia);
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(end_timer);

        }
       
    }
}