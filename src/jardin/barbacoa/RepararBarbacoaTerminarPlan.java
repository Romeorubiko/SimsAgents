package jardin.barbacoa;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.acciones.CocinarComidaBarbacoa;
import ontologia.acciones.Reparar;
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
        getGoalbase().getGoal("terminar_reparar_barbacoa").drop();
        getBeliefbase().getBelief("tiempo_fin_reparar_barbacoa").setFact(new Integer (0));

        IMessageEvent peticion= (IMessageEvent)getBeliefbase().getBelief("mensaje_reparar_barbacoa").getFact();
        Reparar contenido = (Reparar) peticion.getContent();

        Higiene h = contenido.getHigiene();
        Energia e = contenido.getEnergia();
        Mecanica m = contenido.getMecanica();

        h.setGrado(h.getGrado()- Necesidad.NC_POCO);
        e.setGrado(e.getGrado()-Necesidad.NC_POCO);
        m.setExperiencia(m.getExperiencia()+ Habilidad.HB_NORMAL);

        HasReparado response = new HasReparado(e, h, m);

        getBeliefbase().getBelief("obsolescencia").setFact(new Integer (15));
        getBeliefbase().getBelief("estropeado").setFact(Boolean.FALSE);

        IMessageEvent inform = createMessageEvent("has_reparado");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
        inform.setContent(response);
        sendMessage(inform);

        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);

    }
}
