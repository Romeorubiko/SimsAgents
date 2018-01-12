package bano.vater;

import ontologia.acciones.Reparar;
import ontologia.acciones.UsarVater;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.VaterUsado;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;


public class UsarVaterTerminarPlan extends Plan {

    public void body(){

    	int new_timer = (int) (System.currentTimeMillis()/1000 + 100000);

        IMessageEvent peticion= (IMessageEvent)getBeliefbase().getBelief("mensaje_usar_vater").getFact();
        UsarVater contenido = (UsarVater) peticion.getContent();
        
        Vejiga v = contenido.getVejiga();
        v.setGrado(100);
        VaterUsado response = new VaterUsado (v);
        
        IMessageEvent inform = createMessageEvent("vater_usado");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());  
        inform.setContent(response);
        sendMessage(inform);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(new_timer));

    }
}