package bano.vater;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.acciones.Reparar;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Mecanica;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasReparado;

public class TerminarRepararVaterPlan extends Plan{
	public void body() {
		int new_timer = ((int) (System.currentTimeMillis()/1000)) + 100000;
        
        IMessageEvent peticion= (IMessageEvent)getBeliefbase().getBelief("mensaje_reparar_vater").getFact();
        Reparar contenido = (Reparar) peticion.getContent();

        Higiene h = contenido.getHigiene();
        Energia e = contenido.getEnergia();
        Mecanica m = contenido.getMecanica();

        h.setGrado(h.getGrado()- Necesidad.NC_POCO);

        //A más nivel se gasta menos energía parar reparar la barbacoa
        e.setGrado(e.getGrado()-Necesidad.NC_POCO/m.getNivel());

        m.setExperiencia(m.getExperiencia()+ Habilidad.HB_NORMAL);

        HasReparado response = new HasReparado(e, h, m);

        getBeliefbase().getBelief("obsolescencia").setFact(new Integer (100));
        getBeliefbase().getBelief("estropeado").setFact(Boolean.FALSE);

        IMessageEvent inform = createMessageEvent("has_reparado");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
        inform.setContent(response);
        sendMessage(inform);

        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("tiempoFinalizacion_reparar").setFact(new Integer (new_timer));
	}

}
