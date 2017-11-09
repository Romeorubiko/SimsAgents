package bano.ducha;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.DuchaEstropeada;
import ontologia.predicados.OrdenadorEstropeadoNavegarInternet;
import ontologia.predicados.TeHasLavado;

import java.util.*;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;



public class LavarseDuchaPlan extends Plan {

    public void body(){
    	RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Lavarse content = (Lavarse)peticion.getContent();
        
        Higiene higiene = content.getHigiene();
        Energia energia = content.getEnergia();
        
        // Disminuye en uno la cantidad de usos restantes hasta el deterioro de la ducha.
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact() - 1;
        if (obsolescencia <= 0) {
            IMessageEvent respuesta = createMessageEvent("ducha_estropeada");
            DuchaEstropeada duchaEstropeada = new DuchaEstropeada();
            respuesta.setContent(duchaEstropeada);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia);

            energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            higiene.setGrado(higiene.getGrado() + Necesidad.NC_NORMAL);
            content.setHigiene(higiene);
            
        try {
            wait(Accion.TIEMPO_MEDIO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        IMessageEvent respuesta = createMessageEvent("te_has_lavado");
        TeHasLavado teHasLavado = new TeHasLavado(energia, higiene);
        respuesta.setContent(teHasLavado);
        sendMessage(respuesta);
    }
}
}