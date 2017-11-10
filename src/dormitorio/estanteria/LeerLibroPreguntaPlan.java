package dormitorio.estanteria;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.LeerLibro;
import ontologia.acciones.PintarNuevoCuadro;
import ontologia.conceptos.Cuadro;
import ontologia.conceptos.Libro;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.LibroLeido;
import ontologia.predicados.LibroTerminado;

public class LeerLibroPreguntaPlan extends Plan {

    public LeerLibroPreguntaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());
        PintarNuevoCuadro content = (PintarNuevoCuadro) peticion.getContent();

        if (getBeliefbase().getBelief("ocupado_estanteria").getFact().equals(Boolean.TRUE)) {
            IMessageEvent refuse = createMessageEvent("refuse_estanteria");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            IMessageEvent agree = createMessageEvent("agree_caballete");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact();


            getBeliefbase().getBelief("cuadro_instalado").setFact(cuadro);
        getBeliefbase().getBelief("ocupado_caballete").setFact(Boolean.TRUE);
        getBeliefbase().getBelief("mensaje_caballete").setFact(peticion);
        int tiempo = (int) getBeliefbase().getBelief("tiempo_caballete").getFact();
        getBeliefbase().getBelief("tiempo_fin_caballete").setFact(tiempo + Accion.TIEMPO_MEDIO);

        IMessageEvent agree = createMessageEvent("agree_caballete");
        agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
        sendMessage(agree);

        IGoal goal = createGoal("pintar_nuevo_cuadro_tiempo_superado");
        dispatchTopLevelGoal(goal);
    }
}
}
