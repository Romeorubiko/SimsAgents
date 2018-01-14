/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */

package sala_recreativa.minibar;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.acciones.Beber;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.HasBebido;


public class BeberMinibarTerminarPlan extends Plan {
    public void body() {

    	//int new_timer = (int) (System.currentTimeMillis()/1000 + 100000);      

        IMessageEvent peticion= (IMessageEvent)getBeliefbase().getBelief("mensaje_beber_minibar").getFact();
        Beber contenido = (Beber) peticion.getContent();

        Diversion d = contenido.getDiversion();
        Vejiga v = contenido.getVejiga();

        d.setGrado(d.getGrado()+ Necesidad.NC_NORMAL);
        v.setGrado(v.getGrado()-Necesidad.NC_POCO);

        HasBebido response = new HasBebido(contenido.getEnergia(), v, d);

        IMessageEvent inform = createMessageEvent("has_bebido");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
        inform.setContent(response);
        sendMessage(inform);
        
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("tiempo_fin_minibar").setFact(new Integer (0));
    }
}
