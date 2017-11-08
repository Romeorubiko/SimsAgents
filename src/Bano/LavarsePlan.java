package Baño;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;

import java.util.*;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;



public class LavarsePlan extends Plan {

    public void body(){
    	RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        Lavarse content = (Lavarse)peticion.getContent();
        
        Higiene h = content.getHigiene();
        h.setGrado(content.getHigiene().getGrado() - Necesidad.NC_MUCHO);
        content.setHigiene(h);

        Energia e = content.getEnergia();
        e.setGrado(content.getEnergia().getGrado() + Necesidad.NC_POCO);
        content.setEnergia(e);
        
        try {
            wait(Accion.TIEMPO_CORTO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        IMessageEvent respuesta = createMessageEvent("te_has_lavado");
        respuesta.setContent(content);
    }
}
