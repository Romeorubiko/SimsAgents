package jardin.piscina;
import ontologia.acciones.*;
import ontologia.predicados.*;
import ontologia.conceptos.necesidades.*;
import ontologia.conceptos.habilidades.*;
import jadex.runtime.*;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.impl.RMessageEvent;

public class NadarPiscinaTerminarPlan extends Plan {
    public void body() {
        getGoalbase().getGoal("terminar_nadar").drop();
        getBeliefbase().getBelief("tiempo_fin_nadar").setFact(new Integer (0));
        RMessageEvent peticion= (RMessageEvent)getBeliefbase().getBelief("mensaje_nadar").getFact();
        NadarPiscina contenido = (NadarPiscina) peticion.getContent();
        Energia e = contenido.getEnergia();
        Higiene h = contenido.getHigiene();
        Diversion d = contenido.getDiversion();
        Deporte p = contenido.getDeporte();
        e.setGrado(e.getGrado()-Necesidad.NC_POCO);
        h.setGrado(h.getGrado()+Necesidad.NC_POCO);
        d.setGrado(d.getGrado()+Necesidad.NC_POCO);
        p.setExperiencia(p.getExperiencia()+ Habilidad.HB_POCO);
        HasNadadoPiscina response = new HasNadadoPiscina();
        response.setEnergia(e);
        response.setHigiene(h);
        response.setDiversion(d);
        response.setDeporte(p);
        IMessageEvent inform = createMessageEvent("has_nadado");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
        inform.setContent(response);
        sendMessage(inform);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
    }
}