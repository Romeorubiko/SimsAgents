package sala_recreativa.ajedrez;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.acciones.JugarAjedrez;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Logica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.AjedrezJugado;
import ontologia.predicados.AjedrezFallido;

public class JugarAjedrezRespuesta extends Plan {

    @Override
    public void body() {
        RBeliefbase bb;
        bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_ajedrez");
        RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_jugar_ajedrez");
        IMessageEvent request= (IMessageEvent) creenciaMensaje.getFact();
        RBelief creenciaTiempoFinAjedrez=(RBelief) bb.getBelief("tiempo_fin_ajedrez");
        creenciaTiempoFinAjedrez.setValue(0);
        getGoalbase().getGoal("jugar_ajedrez_tiempo_superado").drop();


        JugarAjedrez content = (JugarAjedrez)request.getContent();
        IMessageEvent reto = createMessageEvent("retar_ajedrez");
        IMessageEvent retoSim = sendMessageAndWait(reto);
        if (retoSim.getParameter("performative").getValue().equals(SFipa.AGREE)) {

            Energia energia = content.getEnergia();
            energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            Diversion diversion = content.getDiversion();
            diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
            content.setDiversion(diversion);
            
            InteraccionSocial interaccionsocial = content.getInteraccionSocial();
            interaccionsocial.setGrado(interaccionsocial.getGrado() + Necesidad.NC_NORMAL);
            content.setInteraccionSocial(interaccionsocial);

            Logica logica = content.getLogica();
            logica.setExperiencia(logica.getExperiencia() + Habilidad.HB_MUCHO);
            content.setLogica(logica);


            IMessageEvent inform = createMessageEvent("ajedrez_jugado");
            AjedrezJugado ajedrezJugado = new AjedrezJugado (energia, diversion, interaccionsocial, logica);
            inform.setContent(ajedrezJugado);
            inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(inform);
            creenciaOcupado.setFact(false);
        } else {
            IMessageEvent fail = createMessageEvent("ajedrez_fallido");
            sendMessage(fail);
        }
    }
}  