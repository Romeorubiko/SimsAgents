package jardin.plantas;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.predicados.*;

import java.util.*;

import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;


public class RecogerFrutosPlan extends Plan {

    public void body(){
    		RMessageEvent msg = ((RMessageEvent)getInitialEvent());
    		Boolean libre = (Boolean)getBeliefbase().getBelief("estoy_libre").getFact();
    		Boolean hay_frutos = (Boolean) getBeliefbase().getBelief("hay_frutos").getFact();
    		if (!libre) {
    			IMessageEvent replyRefuse=createMessageEvent("planta_ocupada");
    			replyRefuse.getParameterSet(SFipa.RECEIVERS).addValue(msg.getParameterSet(SFipa.SENDER).getValues());
    			sendMessage(replyRefuse);
    		}
    		 else if (libre && !hay_frutos) {
    	            IMessageEvent replyRefuse2 = createMessageEvent("no_hay_frutos");
    	            replyRefuse2.getParameterSet(SFipa.RECEIVERS).addValue(msg.getParameterSet(SFipa.SENDER).getValues());
        			sendMessage(replyRefuse2);
    	    }
    		else {
    			getBeliefbase().getBelief("estoy_libre").setFact(Boolean.FALSE);
    			IMessageEvent agree = createMessageEvent("planta_no_ocupada");
                agree.getParameterSet(SFipa.RECEIVERS).addValue(msg.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(agree);
                getBeliefbase().getBelief("mensaje_recoger_frutos").setFact(msg);
    	         int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;
    	         getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));
    	         getBeliefbase().getBelief("tiempo_fin_recoger_frutos").setFact(new Integer(end_timer));
    	            IGoal goal= createGoal("terminar_recoger_frutos");
    	            dispatchSubgoal(goal);
    	         getBeliefbase().getBelief("hay_frutos").setFact(Boolean.FALSE);
    	         getBeliefbase().getBelief("numero_veces_cuidada").setFact(0);
    	         getBeliefbase().getBelief("tiempo_maduracion").setFact(100000);
    		}
    	}
    }
