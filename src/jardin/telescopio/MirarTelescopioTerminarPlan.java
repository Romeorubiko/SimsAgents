package jardin.telescopio;
import ontologia.acciones.*;
import ontologia.predicados.*;
import ontologia.conceptos.necesidades.*;
import ontologia.conceptos.habilidades.*;
import jadex.runtime.*;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.impl.RMessageEvent;

public class MirarTelescopioTerminarPlan extends Plan {
    public void body() {
        getGoalbase().getGoal("terminar_mirar_telescopio").drop();
        getBeliefbase().getBelief("tiempo_fin_mirar_telescopio").setFact(new Integer (0));
        RMessageEvent peticion= (RMessageEvent)getBeliefbase().getBelief("mensaje_mirar_telescopio").getFact();
        MirarTelescopio contenido = (MirarTelescopio) peticion.getContent();
        Diversion d = contenido.getDiversion();
        InteraccionSocial i = contenido.getInteraccionSocial();
        Logica l = contenido.getLogica();
        d.setGrado(d.getGrado()+Necesidad.NC_POCO);
        i.setGrado(i.getGrado()-Necesidad.NC_POCO);
        l.setExperiencia(l.getExperiencia()+ Habilidad.HB_NORMAL);
        HasMiradoTelescopio response = new HasMiradoTelescopio();
        response.setDiversion(d);
        response.setInteraccionSocial(i);
        response.setLogica(l);
        IMessageEvent inform = createMessageEvent("has_mirado_telescopio");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
        inform.setContent(response);
        sendMessage(inform);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
    }
}