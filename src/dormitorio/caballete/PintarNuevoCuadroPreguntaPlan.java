package dormitorio.caballete;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.PintarNuevoCuadro;
import ontologia.conceptos.Cuadro;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Pintura;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.CuadroPintado;
import ontologia.predicados.CuadroTerminado;

public class PintarNuevoCuadroPreguntaPlan extends Plan {

    public PintarNuevoCuadroPreguntaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = ((IMessageEvent) getInitialEvent());
        PintarNuevoCuadro content = (PintarNuevoCuadro) peticion.getContent();

        Cuadro cuadro = content.getCuadro();

        if (getBeliefbase().getBelief("cuadro_instalado").getFact() != null) {
            System.out.println("No es posible pintar un cuadro nuevo ya que hay un cuadro instalado en el caballete.");
            IMessageEvent refuse = createMessageEvent("refuse_caballete");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            getBeliefbase().getBelief("cuadro_instalado").setFact(cuadro);
            getBeliefbase().getBelief("mensaje_pintar_nuevo_cuadro").setFact(peticion);

            IMessageEvent agree = createMessageEvent("agree_caballete");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);
        }
    }
}
