package bano.espejo;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.conceptos.habilidades.Carisma;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.EspejoUsado;
import ontologia.predicados.VaterEstropeado;
import ontologia.predicados.VaterUsado;

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
       
            energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            carisma.setExperiencia(carisma.getExperiencia() + Habilidad.HB_NORMAL);
            content.setCarisma(carisma);
            
        try {
            wait(Accion.TIEMPO_MEDIO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        IMessageEvent respuesta = createMessageEvent("espejo_usado");
        EspejoUsado espejoUsado = new EspejoUsado(energia, carisma);
        respuesta.setContent(espejoUsado);
        sendMessage(respuesta);
    }
}
  