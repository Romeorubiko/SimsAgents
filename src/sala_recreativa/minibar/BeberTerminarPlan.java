package sala_recreativa.minibar;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.predicados.HasBebido;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class BeberTerminarPlan extends Plan {
    public void body() {
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);

        Energia e = new Energia();
        Diversion d = new Diversion();
        Vejiga v = new Vejiga();
        Integer grado_e = (Integer)getBeliefbase().getBelief("energia").getFact();
        Integer grado_d = (Integer)getBeliefbase().getBelief("diversion").getFact();
        Integer grado_v = (Integer)getBeliefbase().getBelief("vejiga").getFact();

        e.setGrado(grado_e.intValue());
        d.setGrado(grado_d.intValue()+ Necesidad.NC_POCO);
        v.setGrado(grado_v.intValue()-Necesidad.NC_POCO);

        HasBebido response = new HasBebido();
        response.setEnergia(e);
        response.setDiversion(d);
        response.setVejiga(v);

        IMessageEvent respuesta = createMessageEvent("has_bebido");
        respuesta.setContent(response);
        sendMessage(respuesta);
    }
}
