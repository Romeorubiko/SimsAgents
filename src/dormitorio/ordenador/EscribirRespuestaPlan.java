package dormitorio.ordenador;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.Escribir;
import ontologia.conceptos.habilidades.Escritura;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.EscrituraRealizada;
import ontologia.predicados.OrdenadorEstropeadoEscribir;

public class EscribirRespuestaPlan extends Plan {
    public EscribirRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje").getFact();
        getBeliefbase().getBelief("tiempo_fin").setFact(0);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        Escribir content = (Escribir) peticion.getContent();

        Energia energia = content.getEnergia();
        Diversion diversion = content.getDiversion();
        Escritura escritura = content.getEscritura();

        energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
        diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
        escritura.setExperiencia(escritura.getExperiencia() + Habilidad.HB_NORMAL);

        IMessageEvent respuesta = createMessageEvent("escritura_realizada");
        EscrituraRealizada escrituraRealizada = new EscrituraRealizada(energia, diversion, escritura);
        respuesta.setContent(escrituraRealizada);
        sendMessage(respuesta);
    }
}


