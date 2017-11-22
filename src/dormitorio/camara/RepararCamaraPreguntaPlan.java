package dormitorio.camara;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.RepararCamara;
import ontologia.acciones.SacarFoto;
import ontologia.conceptos.Foto;
import ontologia.predicados.CamaraNoEstropeada;
import ontologia.predicados.CamaraOcupada;

public class RepararCamaraPreguntaPlan extends Plan {

    public RepararCamaraPreguntaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());
        RepararCamara content = (RepararCamara) peticion.getContent();

        Boolean ocupado = (Boolean) getBeliefbase().getBelief("ocupado_camara").getFact();
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia_camara").getFact();

        if (ocupado) {
            IMessageEvent refuse = createMessageEvent("refuse_camara_ocupada");
            refuse.setContent(new CamaraOcupada());
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else if (obsolescencia > 0) {
            IMessageEvent refuse = createMessageEvent("refuse_camara_no_estropeada");
            refuse.setContent(new CamaraNoEstropeada());
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            getBeliefbase().getBelief("ocupado_camara").setFact(Boolean.TRUE);
            getBeliefbase().getBelief("mensaje_camara").setFact(peticion);

            int tiempo = (int) getBeliefbase().getBelief("tiempo_foto").getFact();
            getBeliefbase().getBelief("tiempo_fin_foto").setFact(tiempo + Accion.TIEMPO_MEDIO);

            IMessageEvent agree = createMessageEvent("agree_camara");
            agree.setContent(content);
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            IGoal goal = createGoal("reparar_camara_tiempo_superado");
            dispatchTopLevelGoal(goal);
        }
    }
}
