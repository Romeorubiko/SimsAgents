package sala_recreativa.mesadepingpong;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.acciones.JugarPingpong;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Deporte;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.PingpongJugado;
import ontologia.predicados.PingpongFallido;

public class JugarPingpongRespuesta extends Plan {
    public JugarPingpongRespuesta() {
    }
     @Override
    public void body() {
        RBeliefbase bb;
        bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_pingpong");
        RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_jugar_pingpong");
        IMessageEvent request= (IMessageEvent) creenciaMensaje.getFact();
        RBelief creenciaTiempoFinPingpong=(RBelief) bb.getBelief("tiempo_fin_pingpong");
        creenciaTiempoFinPingpong.setValue(0);
        getGoalbase().getGoal("jugar_pingpong_tiempo_superado").drop();


        JugarPingpong content = (JugarPingpong) request.getContent();
        IMessageEvent reto = createMessageEvent("retar_partido");
        IMessageEvent retoSim = sendMessageAndWait(reto);
        if (retoSim.getParameter("performative").getValue().equals(SFipa.AGREE)) {

             Energia energia = content.getEnergia();
            energia.setGrado(energia.getGrado() - Necesidad.NC_MUCHO);
            content.setEnergia(energia);

            Higiene h = content.getHigiene();
            h.setGrado(h.getGrado() - Necesidad.NC_NORMAL);
            content.setHigiene(h);

            Diversion d = content.getDiversion();
            d.setGrado(d.getGrado() + Necesidad.NC_NORMAL);
            content.setDiversion(d);

            InteraccionSocial i = content.getInteraccionSocial();
            i.setGrado(i.getGrado() + Necesidad.NC_NORMAL);
            content.setInteraccionSocial(i);

            Deporte de = content.getDeporte();
            de.setExperiencia(de.getExperiencia() + Habilidad.HB_NORMAL);
            content.setDeporte(de);


            IMessageEvent inform = createMessageEvent("pingpong_jugado");
            PingpongJugado pingpongJugado = new PingpongJugado(energia, h, d, i, de);
            inform.setContent(pingpongJugado);
            inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(inform);
            creenciaOcupado.setFact(false);
        } else {
            IMessageEvent fail = createMessageEvent("pingpong_fallido");
            sendMessage(fail);
        }
    }
} 