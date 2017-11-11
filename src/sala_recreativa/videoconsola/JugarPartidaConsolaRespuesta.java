package sala_recreativa.videoconsola;

import jadex.runtime.IMessageEvent;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.JugarPartidaConsola;
import ontologia.conceptos.Juego;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.ConsolaEstropeadaPartida;
import ontologia.predicados.PartidoConsolaFinalizado;

public class JugarPartidaConsolaRespuesta extends Plan {
    public JugarPartidaConsolaRespuesta() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje2").getFact();
        getBeliefbase().getBelief("tiempo_fin").setFact(0);
        getBeliefbase().getBelief("ocupado2").setFact(Boolean.FALSE);
        getGoalbase().getGoal("jugar_videojuego_tiempo_superado_2").drop();

        JugarPartidaConsola content = (JugarPartidaConsola) peticion.getContent();
        Energia energia = content.getEnergia();
        Diversion diversion = content.getDiversion();
        InteraccionSocial interaccionsocial = content.getInteraccionSocial();


        energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
        content.setEnergia(energia);

        diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
        content.setDiversion(diversion);

        interaccionsocial.setGrado(content.getInteraccionSocial().getGrado() + Necesidad.NC_NORMAL);
        content.setInteraccionSocial(interaccionsocial);

        IMessageEvent respuesta = createMessageEvent("juego_cooperativo_finalizado");
        PartidoConsolaFinalizado juegoFinalizado = new PartidoConsolaFinalizado(energia, diversion, interaccionsocial);
        respuesta.setContent(juegoFinalizado);
        sendMessage(respuesta);
    }
}