package dormitorio.ordenador;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.Escribir;
import ontologia.conceptos.habilidades.Escritura;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.predicados.OrdenadorEstropeadoEscribir;

public class EscribirPreguntaPlan extends Plan {
    public EscribirPreguntaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());

        if (getBeliefbase().getBelief("ocupado_ordenador").getFact().equals(Boolean.TRUE)) {
            IMessageEvent refuse = createMessageEvent("refuse_ordenador");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            getBeliefbase().getBelief("ocupado_ordenador").setFact(Boolean.TRUE);
            getBeliefbase().getBelief("mensaje_ordenador").setFact(peticion);
            int tiempo = (int) getBeliefbase().getBelief("tiempo_ordenador").getFact();
            getBeliefbase().getBelief("tiempo_fin_ordenador").setFact(tiempo + Accion.TIEMPO_MEDIO);
            Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia_ordenador").getFact();

            IMessageEvent agree = createMessageEvent("agree_ordenador");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            if (obsolescencia <= 0) {
                Escribir content = (Escribir) peticion.getContent();
                Energia energia = content.getEnergia();
                Diversion diversion = content.getDiversion();
                Escritura escritura = content.getEscritura();

                IMessageEvent failure = createMessageEvent("ordenador_estropeado_escribir");
                OrdenadorEstropeadoEscribir ordenadorEstropeadoEscribir = new OrdenadorEstropeadoEscribir(energia, diversion, escritura);
                failure.setContent(ordenadorEstropeadoEscribir);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(failure.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(failure);
            } else {
                getBeliefbase().getBelief("obsolescencia_ordenador").setFact(obsolescencia - 1);

                IGoal goal = createGoal("escribir_tiempo_superado");
                dispatchTopLevelGoal(goal);
            }
        }
    }
}

