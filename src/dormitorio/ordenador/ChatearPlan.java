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
import ontologia.predicados.OrdenadorEstropeadoChatear;

public class ChatearPlan extends Plan {
    public ChatearPlan() {
    }

    @Override
    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        Chatear content = (Chatear) peticion.getContent();

        Energia energia = content.getEnergia();
        InteraccionSocial interaccionSocial = content.getInteraccionSocial();
        Diversion diversion = content.getDiversion();

        // Disminuye en uno la cantidad de usos restantes hasta el deterioro del ordenador.
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact() - 1;

        if (obsolescencia <= 0) {
            IMessageEvent respuesta = createMessageEvent("ordenador_estropeado_chatear");
            OrdenadorEstropeadoChatear ordenadorEstropeadoChatear = new OrdenadorEstropeadoChatear(energia, interaccionSocial, diversion);
            respuesta.setContent(ordenadorEstropeadoChatear);
            sendMessage(respuesta);
        } else {
            getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia);

            energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            interaccionSocial.setGrado(interaccionSocial.getGrado() + Necesidad.NC_NORMAL);
            content.setInteraccionSocial(interaccionSocial);

            diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
            content.setDiversion(diversion);

            try {
                wait(Accion.TIEMPO_MEDIO);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            IMessageEvent respuesta = createMessageEvent("chat_finalizado");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
    }
}
