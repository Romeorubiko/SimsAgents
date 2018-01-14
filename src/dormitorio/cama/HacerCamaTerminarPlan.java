/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */

package dormitorio.cama;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.acciones.HacerLaCama;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.CamaHecha;


public class HacerCamaTerminarPlan extends Plan {
    public void body() {

    	//int new_timer = (int) (System.currentTimeMillis()/1000 + 100000);
        
        IMessageEvent peticion= (IMessageEvent)getBeliefbase().getBelief("mensaje_hacer_cama").getFact();
        HacerLaCama contenido = (HacerLaCama) peticion.getContent();


        Higiene h = contenido.getHigiene();

        h.setGrado(h.getGrado()+ Necesidad.NC_NORMAL);

        CamaHecha response = new CamaHecha(h);

        getBeliefbase().getBelief("cama_hecha").setFact(Boolean.TRUE);

        IMessageEvent inform = createMessageEvent("cama_hecha");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
        inform.setContent(response);
        sendMessage(inform);

        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("tiempo_fin_hacer_cama").setFact(new Integer (0));


    }
}
