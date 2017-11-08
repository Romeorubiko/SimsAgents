package Bano;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.habilidades.Carisma;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;

import java.util.*;
import jadex.runtime.*;
import jadex.adapter.fipa.*;



public class UsarEspejoPlan extends Plan {

    public void body(){
        IMessageEvent message = waitForMessageEvent("usar_espejo");
        UsarEspejo content = (UsarEspejo)message.getContent();
        
        Carisma c = content.getCarisma();
        c.setExperiencia((content.getCarisma().getNivel() + Habilidad.HB_NORMAL));
        content.setCarisma(c);

        Energia e = content.getEnergia();
        e.setGrado(content.getEnergia().getGrado() + Necesidad.NC_NORMAL);
        content.setEnergia(e);
        
        try {
            wait(Accion.TIEMPO_CORTO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        IMessageEvent respuesta = createMessageEvent("espejo_usado");
        respuesta.setContent(content);
    }
}