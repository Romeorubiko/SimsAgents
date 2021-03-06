package dormitorio.camara;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.SacarFoto;
import ontologia.conceptos.Foto;
import ontologia.conceptos.habilidades.Fotografia;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.FotoRealizada;
import ontologia.predicados.CamaraEstropeadaSacarFoto;

public class SacarFotoPreguntaPlan extends Plan {

    public SacarFotoPreguntaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());
        SacarFoto content = (SacarFoto) peticion.getContent();

        Boolean ocupado = (Boolean) getBeliefbase().getBelief("ocupado_camara").getFact();
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia_camara").getFact();

        if (obsolescencia <= 0) {
            IMessageEvent failure = createMessageEvent("camara_estropeada_sacar_foto");
            failure.setContent(content);
            failure.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(failure);
        } else {
            if (ocupado) {
                IMessageEvent refuse = createMessageEvent("refuse_camara_ocupada");
                refuse.setContent(content);
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
            }
        }
    }
}
