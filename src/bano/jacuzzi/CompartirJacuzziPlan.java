package bano.jacuzzi;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.HasCompartidoJacuzzi;
import ontologia.predicados.JacuzziEstropeadoCompartir;
import ontologia.predicados.JacuzziEstropeadoLavarse;
import ontologia.predicados.TeHasLavado;

import java.util.*;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;



public class CompartirJacuzziPlan extends Plan {

    public void body(){
    	
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        CompartirJacuzzi content = (CompartirJacuzzi)peticion.getContent();
        
        InteraccionSocial interaccionsocial = content.getInteraccionSocial();
        Energia energia = content.getEnergia();
       
        // Disminuye en uno la cantidad de usos restantes hasta el deterioro del jacuzzi.
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact() - 1;
        if (obsolescencia <= 0) {
            IMessageEvent respuesta = createMessageEvent("jacuzzi_estropeado_compartir");
            JacuzziEstropeadoCompartir jacuzziEstropeadoCompartir = new JacuzziEstropeadoCompartir();
            respuesta.setContent(jacuzziEstropeadoCompartir);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia);

            energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            interaccionsocial.setGrado(interaccionsocial.getGrado() + Necesidad.NC_NORMAL);
            content.setInteraccionSocial(interaccionsocial);
            
        try {
            wait(Accion.TIEMPO_MEDIO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        IMessageEvent respuesta = createMessageEvent("has_compartido_jacuzzi");
        HasCompartidoJacuzzi hasCompartidoJacuzzi = new HasCompartidoJacuzzi(energia, interaccionsocial);
        respuesta.setContent(hasCompartidoJacuzzi);
        sendMessage(respuesta);
    }
}
}