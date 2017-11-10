package dormitorio.ordenador;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.Chatear;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.ChatFinalizado;
import ontologia.predicados.OrdenadorEstropeadoChatear;

public class ChatearRespuestaPlan extends Plan {
    public ChatearRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje").getFact();
        getBeliefbase().getBelief("tiempo_fin").setFact(0);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getGoalbase().getGoal("chatear_tiempo_superado").drop();

        Chatear content = (Chatear) peticion.getContent();

        Energia energia = content.getEnergia();
        InteraccionSocial interaccionSocial = content.getInteraccionSocial();
        Diversion diversion = content.getDiversion();

        energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
        content.setEnergia(energia);

        interaccionSocial.setGrado(interaccionSocial.getGrado() + Necesidad.NC_NORMAL);
        content.setInteraccionSocial(interaccionSocial);

        diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
        content.setDiversion(diversion);

        IMessageEvent respuesta = createMessageEvent("chat_finalizado");
        ChatFinalizado chatFinalizado = new ChatFinalizado(energia, interaccionSocial, diversion);
        respuesta.setContent(chatFinalizado);
        sendMessage(respuesta);
    }
}