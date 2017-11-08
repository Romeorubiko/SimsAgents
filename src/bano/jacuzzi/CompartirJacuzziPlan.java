package Bano;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;

import java.util.*;
import jadex.runtime.*;
import jadex.adapter.fipa.*;



public class CompartirJacuzziPlan extends Plan {

    public void body(){
        IMessageEvent message = waitForMessageEvent("compartir_jacuzzi");
        CompartirJacuzzi content = (CompartirJacuzzi)message.getContent();
        
        InteraccionSocial i = content.getInteraccionSocial();
        i.setGrado(content.getInteraccionSocial().getGrado() - Necesidad.NC_MUCHO);
        content.setInteraccionSocial(i);

        Energia e = content.getEnergia();
        e.setGrado(content.getEnergia().getGrado() + Necesidad.NC_NORMAL);
        content.setEnergia(e);
        
        try {
            wait(Accion.TIEMPO_CORTO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        IMessageEvent respuesta = createMessageEvent("has_compartido_jacuzzi");
        respuesta.setContent(content);
    }
}