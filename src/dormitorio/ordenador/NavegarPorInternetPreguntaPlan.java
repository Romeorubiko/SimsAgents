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
import ontologia.predicados.OrdenadorEstropeadoNavegarInternet;

import java.util.ArrayList;

public class NavegarPorInternetPreguntaPlan extends Plan {
    public NavegarPorInternetPreguntaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());

        if (getBeliefbase().getBelief("ocupado").getFact().equals(Boolean.TRUE)) {
            IMessageEvent refuse = createMessageEvent("refuse");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            getBeliefbase().getBelief("mensaje").setFact(peticion);
            int tiempo = (int) getBeliefbase().getBelief("tiempo").getFact();
            getBeliefbase().getBelief("tiempo_fin").setFact(tiempo + Accion.TIEMPO_MEDIO);
            Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact();

            IMessageEvent agree = createMessageEvent("agree");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            if (obsolescencia <= 0) {
                Chatear content = (Chatear) peticion.getContent();
                Energia energia = content.getEnergia();
                Diversion diversion = content.getDiversion();

                IMessageEvent failure = createMessageEvent("ordenador_estropeado_navegar_internet");
                OrdenadorEstropeadoNavegarInternet ordenadorEstropeadoChatear = new OrdenadorEstropeadoNavegarInternet(energia, diversion);
                failure.setContent(ordenadorEstropeadoChatear);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(failure.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(failure);
            } else {
                getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia - 1);

                IGoal goal = createGoal("navegar_internet_tiempo_superado");
                dispatchTopLevelGoal(goal);
            }
        }
    }
}
