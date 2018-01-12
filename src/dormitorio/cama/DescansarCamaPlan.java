/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */


package dormitorio.cama;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import jadex.runtime.*;
import ontologia.acciones.Descansar;


public class DescansarCamaPlan extends Plan {
    public void body(){

        IMessageEvent peticion = ((IMessageEvent)getInitialEvent());
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Descansar content = (Descansar) peticion.getContent();

        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("cama_ocupada");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            refuse.setContent(content);
            sendMessage(refuse);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            IMessageEvent agree = createMessageEvent("cama_no_ocupada");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
            agree.setContent(content);
            sendMessage(agree);

            getBeliefbase().getBelief("mensaje_descansar_cama").setFact(peticion);

            int end_timer = (int) (System.currentTimeMillis()/1000) + Accion.TIEMPO_CORTO;
            getBeliefbase().getBelief("tiempo_fin_descansar_cama").setFact(new Integer(end_timer));

        }
    }





}
