package jardin.barbacoa;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.CocinarComidaBarbacoa;
import ontologia.conceptos.habilidades.Cocina;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.predicados.BarbacoaRota;
import jadex.adapter.fipa.SFipa;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class CocinarBarbacoaPlan extends Plan {
    public void body() {
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        CocinarComidaBarbacoa content = (CocinarComidaBarbacoa) peticion.getContent();
        Hambre hmb = content.getHambre();
        Higiene h = content.getHigiene();
        Diversion d = content.getDiversion();
        Cocina c = content.getCocina();
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean estropeado = (Boolean)getBeliefbase().getBelief("estropeado").getFact();

        if(ocupado.booleanValue()) {
            IMessageEvent refuse = createMessageEvent("barbacoa_ocupada_cocinar");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }
        else if (estropeado.booleanValue()) {
            IMessageEvent failure = createMessageEvent("barbacoa_estropeada");
            BarbacoaRota response = new BarbacoaRota (h, hmb, d, c);
            failure.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            failure.setContent(response);
            sendMessage(failure);
        }
        else if (((Integer) getBeliefbase().getBelief("obsolescencia").getFact()).intValue() - 1<=0){
            getBeliefbase().getBelief("estropeado").setFact(Boolean.TRUE);
            IMessageEvent failure = createMessageEvent("barbacoa_estropeada");
            failure.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            BarbacoaRota response = new BarbacoaRota (h, hmb, d, c);
            failure.setContent(response);
            sendMessage(failure);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);

            int obsolescencia = ((Integer) getBeliefbase().getBelief("obsolescencia").getFact()).intValue() - 1;
            getBeliefbase().getBelief("obsolescencia").setFact(new Integer (obsolescencia));

            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));


        }

    }
}
