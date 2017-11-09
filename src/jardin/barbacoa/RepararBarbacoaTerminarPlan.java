package jardin.barbacoa;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Mecanica;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasReparado;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class RepararBarbacoaTerminarPlan extends Plan {
    public void body() {
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("estropeado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("obsolescencia").setFact(new Integer (15));
        Higiene h = new Higiene();
        Energia e = new Energia();
        Mecanica m = new Mecanica();

        Integer grado_h = (Integer)getBeliefbase().getBelief("higiene").getFact();
        Integer grado_e = (Integer)getBeliefbase().getBelief("energia").getFact();
        Integer experiencia_m = (Integer)getBeliefbase().getBelief("experiencia_mecanica").getFact();

        h.setGrado(grado_h.intValue()- Necesidad.NC_POCO);
        e.setGrado(grado_e.intValue()-Necesidad.NC_POCO);
        m.setExperiencia(experiencia_m.intValue()+ Habilidad.HB_NORMAL);

        HasReparado response = new HasReparado();
        response.setHigiene(h);
        response.setEnergia(e);
        response.setMecanica(m);

        IMessageEvent respuesta = createMessageEvent("has_reparado");
        respuesta.setContent(response);
        sendMessage(respuesta);

    }
}
