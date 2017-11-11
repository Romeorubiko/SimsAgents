package dormitorio.camara;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.SacarFotoRetrato;
import ontologia.conceptos.Foto;
import ontologia.conceptos.Retrato;
import ontologia.conceptos.habilidades.Fotografia;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.CamaraEstropeadaSacarFotoRetrato;
import ontologia.predicados.FotoRealizada;

public class SacarFotoRetratoPreguntaPlan extends Plan {
    //TENER EN CUENTA DONDE RESTAMOS LA OBSOLESCENCIA !.

    public SacarFotoRetratoPreguntaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());
        SacarFotoRetrato content = (SacarFotoRetrato) peticion.getContent();
        Retrato retrato = content.getRetrato();

        Boolean ocupado = (Boolean) getBeliefbase().getBelief("ocupado_camara").getFact();

        // Disminuye en uno la cantidad de usos restantes hasta el deterioro de la cámara.
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia_camara").getFact();

        if (obsolescencia <= 0) {
            IMessageEvent refuse = createMessageEvent("camara_estropeada_sacar_foto_retrato");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            if (ocupado) {
                //Camara ocupada
                IMessageEvent refuse = createMessageEvent("refuse_camara");
                refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(refuse);
            } else {
                //Si capara libre pregunto si el otro sim quiere posar conmigo
                IMessageEvent posar = createMessageEvent("posar");
                IMessageEvent respuestaOtroSim = sendMessageAndWait(posar);
                if (respuestaOtroSim.getParameter("performative").getValue().equals(SFipa.AGREE)) {
                    getBeliefbase().getBelief("ocupado_camara").setFact(Boolean.TRUE);
                    getBeliefbase().getBelief("mensaje_camara").setFact(peticion);
                    int tiempo = (int) getBeliefbase().getBelief("tiempo_foto").getFact();
                    getBeliefbase().getBelief("tiempo_fin_foto").setFact(tiempo + Accion.TIEMPO_MEDIO);
                    IMessageEvent agree = createMessageEvent("agree_camara");
                    agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
                    sendMessage(agree);

                    IGoal goal = createGoal("sacar_foto_retrato_tiempo_superado");
                    dispatchTopLevelGoal(goal);
                } else {
                    //No me acepto por tanto no realizo la accion
                    IMessageEvent refuse = createMessageEvent("sim_no_posa");
                    refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
                    sendMessage(refuse);
                }

            }
        }
    }
}
