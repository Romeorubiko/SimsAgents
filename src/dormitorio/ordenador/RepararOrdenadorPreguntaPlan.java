package dormitorio.ordenador;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.RepararOrdenador;
import ontologia.predicados.OrdenadorNoEstropeado;
import ontologia.predicados.OrdenadorOcupado;

public class RepararOrdenadorPreguntaPlan extends Plan {

    public RepararOrdenadorPreguntaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());
        RepararOrdenador content = (RepararOrdenador) peticion.getContent();

        Boolean ocupado = (Boolean) getBeliefbase().getBelief("ocupado").getFact();
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact();

        if (ocupado) {
            IMessageEvent refuse = createMessageEvent("refuse_ordenador_ocupado");
            refuse.setContent(new OrdenadorOcupado());
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else if (obsolescencia > 0) {
            IMessageEvent refuse = createMessageEvent("refuse_ordenador_no_estropeado");
            refuse.setContent(new OrdenadorNoEstropeado());
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            getBeliefbase().getBelief("mensaje").setFact(peticion);

            int tiempo = (int) getBeliefbase().getBelief("tiempo").getFact();
            getBeliefbase().getBelief("tiempo_fin").setFact(tiempo + Accion.TIEMPO_MEDIO);

            IMessageEvent agree = createMessageEvent("agree");
            agree.setContent(content);
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);
        }
    }
}
