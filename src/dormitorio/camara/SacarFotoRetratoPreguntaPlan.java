package dormitorio.camara;

import jadex.adapter.fipa.*;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.SacarFotoRetrato;
import ontologia.conceptos.Retrato;

import java.util.Random;

public class SacarFotoRetratoPreguntaPlan extends Plan {
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
            IMessageEvent failure = createMessageEvent("camara_estropeada_sacar_foto_retrato");
            failure.setContent(content);
            failure.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(failure);
        } else {
            if (ocupado) {
                //Camara ocupada
                IMessageEvent refuse = createMessageEvent("refuse_camara_ocupada");
                refuse.setContent(content);
                refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(refuse);
            } else {
                //Si camara libre pregunto si el otro sim quiere posar conmigo
                IMessageEvent posar = createMessageEvent("posar");
                posar.getParameterSet(SFipa.RECEIVERS).addValue(buscarAgente());
                posar.getParameterSet(SFipa.CONVERSATION_ID).addValue(SFipa.createUniqueId("posar"));
                IMessageEvent respuestaOtroSim = sendMessageAndWait(posar);

                if (respuestaOtroSim.getParameter("performative").getValue().equals(SFipa.AGREE)) {
                    getBeliefbase().getBelief("ocupado_camara").setFact(Boolean.TRUE);
                    getBeliefbase().getBelief("mensaje_camara").setFact(peticion);
                    int tiempo = (int) getBeliefbase().getBelief("tiempo_foto").getFact();
                    getBeliefbase().getBelief("tiempo_fin_foto").setFact(tiempo + Accion.TIEMPO_MEDIO);

                    IMessageEvent agree = createMessageEvent("agree_camara");
                    agree.setContent(content);
                    agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
                    sendMessage(agree);

                    IGoal goal = createGoal("sacar_foto_retrato_tiempo_superado");
                    dispatchTopLevelGoal(goal);
                } else {
                    //No me acepto por tanto no realizo la accion
                    IMessageEvent refuse = createMessageEvent("sim_no_posa");
                    refuse.setContent(content);
                    refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
                    sendMessage(refuse);
                }

            }
        }
    }

    private AgentIdentifier buscarAgente() {
        ServiceDescription serviceDescription = new ServiceDescription("Posar", "", "");
        AgentDescription agentDescription = new AgentDescription();
        agentDescription.addService(serviceDescription);
        SearchConstraints sc = new SearchConstraints();
        sc.setMaxResults(-1);
        IGoal busqueda = createGoal("df_search");
        busqueda.getParameter("constraints").setValue(sc);
        busqueda.getParameter("description").setValue(agentDescription);
        dispatchSubgoalAndWait(busqueda);
        AgentDescription[] result = (AgentDescription[]) busqueda.getParameterSet("result").getValues();
        return result[new Random().nextInt(result.length)].getName();
    }
}
