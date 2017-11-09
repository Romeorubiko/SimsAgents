package sala_recreativa.maquina_ejercicios;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.conceptos.habilidades.Deporte;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasEntrenado;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class EntrenarTerminarPlan extends Plan {
    public void body() {
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);

        Energia e = new Energia();
        Higiene h = new Higiene();
        Hambre hmb = new Hambre();
        Deporte d = new Deporte();

        Integer grado_e = (Integer)getBeliefbase().getBelief("energia").getFact();
        Integer grado_h = (Integer)getBeliefbase().getBelief("higiene").getFact();
        Integer grado_hmb = (Integer)getBeliefbase().getBelief("hambre").getFact();
        Integer experiencia_d = (Integer)getBeliefbase().getBelief("deporte").getFact();

        e.setGrado(grado_e.intValue()- Necesidad.NC_POCO);
        h.setGrado(grado_h.intValue()-Necesidad.NC_POCO);
        hmb.setGrado(grado_hmb.intValue()-Necesidad.NC_POCO);
        d.setExperiencia(experiencia_d.intValue()+ Habilidad.HB_MUCHO);

        HasEntrenado response = new HasEntrenado();
        response.setEnergia(e);
        response.setHigiene(h);
        response.setHambre(hmb);
        response.setDeporte(d);

        IMessageEvent respuesta = createMessageEvent("has_entrenado");
        respuesta.setContent(response);
        sendMessage(respuesta);
    }
}
