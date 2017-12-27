package jardin.plantas;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.predicados.*;
import ontologia.conceptos.habilidades.Jardineria;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;

import java.util.*;

import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;


public class CuidarPlantasPlan extends Plan {

    public void body(){
    		RMessageEvent msg = ((RMessageEvent)getInitialEvent());
    		Boolean libre = (Boolean)getBeliefbase().getBelief("estoy_libre").getFact();
    		if (libre) {
    			getBeliefbase().getBelief("estoy_libre").setFact(Boolean.FALSE);
    			IMessageEvent agree = createMessageEvent("planta_no_ocupada");
                agree.getParameterSet(SFipa.RECEIVERS).addValue(msg.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(agree);
                getBeliefbase().getBelief("mensaje_cuidar_plantas").setFact(msg);
    			int numVecesCuidada =(Integer) getBeliefbase().getBelief("numero_veces_cuidada").getFact();
    			getBeliefbase().getBelief("numero_veces_cuidada").setFact(numVecesCuidada+1);
    			if(numVecesCuidada>=2) {
    				 getBeliefbase().getBelief("tiempo_maduracion").setFact((Integer)getBeliefbase().getBelief("tiempo_maduracion").getFact()-1000);
    			}
    	         int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;
    	         getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));
    	         getBeliefbase().getBelief("tiempo_fin_cuidar_plantas").setFact(new Integer(end_timer));
    	            IGoal goal= createGoal("terminar_cuidar_plantas");
    	            dispatchSubgoal(goal);
    		}
    		else {
    			IMessageEvent replyRefuse=createMessageEvent("planta_ocupada");
    			replyRefuse.getParameterSet(SFipa.RECEIVERS).addValue(msg.getParameterSet(SFipa.SENDER).getValues());
    			sendMessage(replyRefuse);
    		}
    }
}