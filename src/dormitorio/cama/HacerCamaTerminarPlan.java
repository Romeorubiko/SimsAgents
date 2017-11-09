package dormitorio.cama;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.CamaHecha;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class HacerCamaTerminarPlan extends Plan {
    public void body() {
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("cama_hecha").setFact(Boolean.TRUE);

        Energia e = new Energia();
        Higiene h = new Higiene();

        Integer grado_e = (Integer)getBeliefbase().getBelief("energia").getFact();
        Integer grado_h = (Integer)getBeliefbase().getBelief("higiene").getFact();

        e.setGrado(grado_e.intValue()- Necesidad.NC_POCO);
        h.setGrado(grado_h.intValue()-Necesidad.NC_POCO);

        CamaHecha response = new CamaHecha();
        response.setEnergia(e);
        response.setHigiene(h);

        IMessageEvent respuesta = createMessageEvent("cama_hecha");
        respuesta.setContent(response);
        sendMessage(respuesta);

    }
}
