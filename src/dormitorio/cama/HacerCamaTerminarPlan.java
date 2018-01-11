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

        //getGoalbase().getGoal("terminar_hacer_cama").drop();
    	int new_timer = (int) (System.currentTimeMillis() + 100000);
        getBeliefbase().getBelief("tiempo_fin_hacer_cama").setFact(new Integer (new_timer));
        RMessageEvent peticion= (RMessageEvent)getBeliefbase().getBelief("mensaje_hacer_cama").getFact();
        HacerLaCama contenido = (HacerLaCama) peticion.getContent();

        Energia e = contenido.getEnergia();
        Higiene h = contenido.getHigiene();

        e.setGrado(e.getGrado()- Necesidad.NC_POCO);
        h.setGrado(h.getGrado()+ Necesidad.NC_POCO);

        CamaHecha response = new CamaHecha(e, h);

        getBeliefbase().getBelief("cama_hecha").setFact(Boolean.TRUE);

        IMessageEvent inform = createMessageEvent("cama_hecha");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
        inform.setContent(response);
        sendMessage(inform);

        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);


    }
}
