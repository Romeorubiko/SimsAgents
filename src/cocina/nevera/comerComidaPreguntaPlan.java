package cocina.nevera;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.ComerComida;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Energia;
import ontologia.predicados.NeveraEstropeadaComer;

import java.util.ArrayList;

public class comerComidaPreguntaPlan extends Plan {
    public comerComidaPreguntaPlan() {
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
            	ComerComida content = (ComerComida) peticion.getContent();
                Energia energia = content.getEnergia();
                Hambre hambre = content.getHambre();

                IMessageEvent failure = createMessageEvent("nevera_estropeada_comer");
                NeveraEstropeadaComer neveraEstropeadaComer = new NeveraEstropeadaComer(energia, hambre);
                failure.setContent(neveraEstropeadaComer);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(failure.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(failure);
            } else {
                getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia - 1);

                IGoal goal = createGoal("comer_comida_tiempo_superado");
                dispatchTopLevelGoal(goal);
            }
        }
    }
}