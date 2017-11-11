package cocina.nevera;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.Beber;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Vejiga;
import ontologia.conceptos.necesidades.Energia;
import ontologia.predicados.NeveraEstropeadaBeber;

import java.util.ArrayList;

public class beberPreguntaPlan extends Plan {
    public beberPreguntaPlan() {
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
                Beber content = (Beber) peticion.getContent();
                Energia energia = content.getEnergia();
                Vejiga vejiga = content.getVejiga();
				Diversion diversion = content.getDiversion();

                IMessageEvent failure = createMessageEvent("nevera_estropeada_beber");
                NeveraEstropeadaBeber neveraEstropeadaBeber = new NeveraEstropeadaBeber(energia, vejiga, diversion);
                failure.setContent(neveraEstropeadaBeber);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(failure.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(failure);
            } else {
                getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia - 1);

                IGoal goal = createGoal("beber_tiempo_superado");
                dispatchTopLevelGoal(goal);
            }
        }
    }
}