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

/**
 * Created by eldgb on 09-Nov-17.
 */
public class HacerCamaTerminarPlan extends Plan {
    public void body() {

        getGoalbase().getGoal("terminar_hacer_cama").drop();
        getBeliefbase().getBelief("tiempo_fin_hacer_cama").setFact(new Integer (0));
        RMessageEvent peticion= (RMessageEvent)getBeliefbase().getBelief("mensaje_hacer_cama").getFact();
        HacerLaCama contenido = (HacerLaCama) peticion.getContent();

        Energia e = contenido.getEnergia();
        Higiene h = contenido.getHigiene();

        e.setGrado(e.getGrado()- Necesidad.NC_POCO);
        h.setGrado(h.getGrado()+ Necesidad.NC_POCO);

        CamaHecha response = new CamaHecha(e, h);

        getBeliefbase().getBelief("cama_hecha").setFact(Boolean.TRUE);

        IMessageEvent inform = createMessageEvent("cama_hecha");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
        inform.setContent(response);
        sendMessage(inform);

        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);


    }
}
