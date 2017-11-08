package Bano;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;

import java.util.*;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;



public class UsarVaterPlan extends Plan {

    public void body(){
        IMessageEvent message = waitForMessageEvent("usar_vater");
        UsarVater content = (UsarVater)message.getContent();
               
        Vejiga g = content.getVejiga();
        g.setGrado(content.getVejiga().getGrado() - Necesidad.NC_MUCHO);
        content.setVejiga(g);

        Energia e = content.getEnergia();
        e.setGrado(content.getEnergia().getGrado() + Necesidad.NC_POCO);
        content.setEnergia(e);
        
        try {
            wait(Accion.TIEMPO_CORTO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        IMessageEvent respuesta = createMessageEvent("vater_usado");
        respuesta.setContent(content);
    }
}
