package dormitorio.ordenador;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.Chatear;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.predicados.OrdenadorEstropeadoChatear;

import java.util.ArrayList;

public class ChatearPreguntaPlan extends Plan {
    public ChatearPreguntaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());

        if (getBeliefbase().getBelief("ocupado_ordenador").getFact().equals(Boolean.TRUE)) {
            IMessageEvent refuse = createMessageEvent("refuse_ordenador");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            getBeliefbase().getBelief("ocupado_ordenador").setFact(Boolean.TRUE);
            getBeliefbase().getBelief("mensaje_ordenador").setFact(peticion);
            int tiempo = (int) getBeliefbase().getBelief("tiempo_ordenador").getFact();
            getBeliefbase().getBelief("tiempo_fin_ordenador").setFact(tiempo + Accion.TIEMPO_MEDIO);
            Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia_ordenador").getFact();

            IMessageEvent agree = createMessageEvent("agree_ordenador");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            if (obsolescencia <= 0) {
                Chatear content = (Chatear) peticion.getContent();
                Energia energia = content.getEnergia();
                InteraccionSocial interaccionSocial = content.getInteraccionSocial();
                Diversion diversion = content.getDiversion();

                IMessageEvent failure = createMessageEvent("ordenador_estropeado_chatear");
                OrdenadorEstropeadoChatear ordenadorEstropeadoChatear = new OrdenadorEstropeadoChatear(energia, interaccionSocial, diversion);
                failure.setContent(ordenadorEstropeadoChatear);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(failure.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(failure);
            } else {
                getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia - 1);

                IGoal goal = createGoal("chatear_tiempo_superado");
                dispatchTopLevelGoal(goal);
            }
        }
    }
}
