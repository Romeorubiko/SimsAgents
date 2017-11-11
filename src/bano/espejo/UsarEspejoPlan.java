package bano.espejo;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.habilidades.Carisma;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.DuchaEstropeada;
import ontologia.predicados.OrdenadorEstropeadoNavegarInternet;
import ontologia.predicados.TeHasLavado;
import ontologia.predicados.VaterEstropeado;

import java.util.*;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;



public class UsarEspejoPlan extends Plan {

    public void body(){
    	RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        UsarEspejo content = (UsarEspejo)peticion.getContent();
        
        Carisma carisma = content.getCarisma();
        Energia energia = content.getEnergia();
        
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        
        if(ocupado.booleanValue()) {
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

           
            getBeliefbase().getBelief("experiencia_carisma").setFact(new Integer(experiencia_carisma));
            getBeliefbase().getBelief("nivel_carisma").setFact(new Integer(nivel_carisma));
            getBeliefbase().getBelief("energia").setFact(new Integer(grado_energia));
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));

        }
       
}
}