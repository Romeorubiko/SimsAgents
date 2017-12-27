package cocina.fogones;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.CocinarComida;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Energia;
import ontologia.predicados.FogonesEstropeados;

import java.util.ArrayList;

public class cocinarComidaPreguntaPlan extends Plan {
    public cocinarComidaPreguntaPlan() {
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
            	CocinarComida content = (CocinarComida) peticion.getContent();
                Energia energia = content.getEnergia();
                Hambre hambre = content.getHambre();

                IMessageEvent failure = createMessageEvent("fogones_estropeados_cocinar_comida");
                FogonesEstropeados fogonesEstropeados = new FogonesEstropeados(energia, hambre);
                failure.setContent(fogonesEstropeados);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(failure.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(failure);
            } else {
                getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia - 1);

                IGoal goal = createGoal("cocinar_comida_tiempo_superado");
                dispatchTopLevelGoal(goal);
            }
        }
    }
}