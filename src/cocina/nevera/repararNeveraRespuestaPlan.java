package cocina.nevera;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.acciones.Reparar;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Mecanica;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasReparado;


public class repararNeveraRespuestaPlan extends Plan {

	public void body() {

		getGoalbase().getGoal("terminar_reparar_nevera").drop();
        getBeliefbase().getBelief("tiempo_fin_reparar_nevera").setFact(new Integer (0));

        RMessageEvent peticion= (RMessageEvent)getBeliefbase().getBelief("mensaje_reparar_nevera").getFact();
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
