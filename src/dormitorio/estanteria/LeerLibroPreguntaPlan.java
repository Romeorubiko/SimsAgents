package dormitorio.estanteria;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.LeerLibro;

import java.util.ArrayList;

public class LeerLibroPreguntaPlan extends Plan {

    public LeerLibroPreguntaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());
        LeerLibro content = (LeerLibro) peticion.getContent();

        if (getBeliefbase().getBelief("ocupado_estanteria").getFact().equals(Boolean.TRUE)) {
            IMessageEvent refuse = createMessageEvent("refuse_estanteria");
            refuse.setContent(content);
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            getBeliefbase().getBelief("ocupado_estanteria").setFact(Boolean.TRUE);
            int tiempo = (int) getBeliefbase().getBelief("tiempo_estanteria").getFact();

            IMessageEvent agree = createMessageEvent("agree_estanteria");
            agree.setContent(content);
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) getBeliefbase().getBelief("mensajes_estanteria").getFact();
            ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) getBeliefbase().getBelief("tiempos_fin_estanteria").getFact();

            // Se actualiza el array de mensajes añadiendo el mensaje del sim actual en la ultima posicion
            arrayMensajes.add(peticion);
            getBeliefbase().getBelief("mensajes_estanteria").setFact(arrayMensajes);

            // Se actualiza el array de tiempos de finalización de las estanteria añadiendo la ultima
            arrayTiempos.add(tiempo + Accion.TIEMPO_MEDIO);
            getBeliefbase().getBelief("tiempos_fin_estanteria").setFact(arrayTiempos);

            if (arrayMensajes.isEmpty()) {
                IGoal goal = createGoal("leer_libro_tiempo_superado");
                dispatchTopLevelGoal(goal);
            }
        }
    }
}
