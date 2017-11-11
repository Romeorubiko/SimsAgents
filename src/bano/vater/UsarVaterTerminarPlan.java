package bano.vater;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.VaterUsado;

import java.util.*;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;



public class UsarVaterTerminarPlan extends Plan {

    public void body(){
    	
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("estropeado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("obsolescencia").setFact(new Integer (100));
        Vejiga vejiga = new Vejiga();
        Energia energia = new Energia();
         
        Integer grado_higiene = (Integer)getBeliefbase().getBelief("higiene").getFact();
        Integer grado_energia = (Integer)getBeliefbase().getBelief("energia").getFact();
       
        vejiga.setGrado(grado_higiene.intValue()- Necesidad.NC_NORMAL);
        energia.setGrado(grado_energia.intValue()+ Necesidad.NC_POCO);
        
        VaterUsado response = new VaterUsado();
        response.setVejiga(vejiga);
        response.setEnergia(energia);
 

        IMessageEvent respuesta = createMessageEvent("vater_usado");
        respuesta.setContent(response);
        sendMessage(respuesta);
}
}