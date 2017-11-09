package bano.vater;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.LavaboEstropeadoLavarse;
import ontologia.predicados.TeHasLavado;
import ontologia.predicados.VaterEstropeado;
import ontologia.predicados.VaterUsado;

import java.util.*;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;



public class UsarVaterPlan extends Plan {

    public void body(){
    	RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        UsarVater content = (UsarVater)peticion.getContent();
               
        Vejiga vejiga = content.getVejiga();
        Energia energia = content.getEnergia();
       
        // Disminuye en uno la cantidad de usos restantes hasta el deterioro del.
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact() - 1;
        if (obsolescencia <= 0) {
            IMessageEvent respuesta = createMessageEvent("vater_estropeado");
            VaterEstropeado vaterEstropeado = new VaterEstropeado();
            respuesta.setContent(vaterEstropeado);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia);

            energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            vejiga.setGrado(vejiga.getGrado() + Necesidad.NC_NORMAL);
            content.setVejiga(vejiga);
            
        try {
            wait(Accion.TIEMPO_MEDIO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        IMessageEvent respuesta = createMessageEvent("te_has_lavado");
        VaterUsado vaterUsado = new VaterUsado(energia, vejiga);
        respuesta.setContent(vaterUsado);
        sendMessage(respuesta);
    }
}
}     
        
     