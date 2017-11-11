package sala_recreativa.videoconsola;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.JugarPartidaConsola;
import ontologia.conceptos.Juego;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Logica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.ConsolaEstropeadaPartida;



public class JugarPartidaConsolaPregunta extends Plan {
    public JugarPartidaConsolaPregunta() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());

        if (getBeliefbase().getBelief("ocupado").getFact().equals(Boolean.FALSE)) {
            IMessageEvent refuse = createMessageEvent("refuse");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }
        if (getBeliefbase().getBelief("ocupado2").getFact().equals(Boolean.TRUE)) {
            IMessageEvent refuse = createMessageEvent("refuse");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            getBeliefbase().getBelief("ocupado2").setFact(Boolean.TRUE);
            getBeliefbase().getBelief("mensaje2").setFact(peticion);
            int tiempo = ((Integer) getBeliefbase().getBelief("tiempo2").getFact()).intValue();
            getBeliefbase().getBelief("tiempo_fin").setFact(tiempo + Accion.TIEMPO_MEDIO);
            Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact();

            IMessageEvent agree = createMessageEvent("agree");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            if (obsolescencia <= 0) {
                JugarPartidaConsola content = (JugarPartidaConsola) peticion.getContent();
                Energia energia = content.getEnergia();
                Diversion diversion = content.getDiversion();
                InteraccionSocial interaccionsocial = content.getInteraccionSocial();

                IMessageEvent failure = createMessageEvent("consola_estropeada_partida");
                ConsolaEstropeadaPartida consolaEstropeadaPartida = new ConsolaEstropeadaPartida(energia, diversion, interaccionsocial);
                failure.setContent(consolaEstropeadaPartida);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(failure.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(failure);
            } else {
                getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia - 1);

                IGoal goal = createGoal("jugar_videojuego_tiempo_superado_2");
                dispatchTopLevelGoal(goal);
            }
        }
    }
}