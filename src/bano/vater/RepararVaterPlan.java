package bano.vater;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.Reparar;

public class RepararVaterPlan extends Plan{
	public void body() {
		System.out.println("Reparar vater plan");
		IMessageEvent peticion = (IMessageEvent)getInitialEvent();
        Reparar content = (Reparar) peticion.getContent();
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean estropeado = (Boolean)getBeliefbase().getBelief("estropeado").getFact();
        System.out.println(estropeado);

        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("vater_ocupado");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            refuse.setContent(content);
            sendMessage(refuse);
        }

        else if (!estropeado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("objeto_no_estropeado");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            refuse.setContent(content);
            sendMessage(refuse);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            IMessageEvent agree = createMessageEvent("vater_reparar_no_ocupado");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            agree.setContent(content);
            sendMessage(agree);

            getBeliefbase().getBelief("mensaje_reparar_vater").setFact(peticion);


            int end_timer = (int) System.currentTimeMillis()/1000 + Accion.TIEMPO_MEDIO;
            getBeliefbase().getBelief("tiempoFinalizacion_reparar").setFact(new Integer(end_timer));
		
	}
	}
}
