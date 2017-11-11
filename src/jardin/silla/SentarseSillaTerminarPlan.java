package jardin.silla;
import ontologia.acciones.*;
import ontologia.predicados.*;
import ontologia.conceptos.necesidades.*;
import jadex.runtime.*;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.impl.RMessageEvent;

public class SentarseSillaTerminarPlan extends Plan {
    public void body() {
        getGoalbase().getGoal("terminar_sentarse_silla").drop();
        getBeliefbase().getBelief("tiempo_fin_sentarse_silla").setFact(new Integer (0));
        RMessageEvent peticion= (RMessageEvent)getBeliefbase().getBelief("mensaje_sentarse_silla").getFact();
        SentarseSilla contenido = (SentarseSilla) peticion.getContent();
        Energia e  = contenido.getEnergia();
        e.setGrado(e.getGrado()+ Necesidad.NC_POCO);
        TeHasSentado response = new TeHasSentado();
        response.setEnergia(e);
        IMessageEvent inform = createMessageEvent("te_has_sentado");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
        inform.setContent(response);
        sendMessage(inform);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
    }
}