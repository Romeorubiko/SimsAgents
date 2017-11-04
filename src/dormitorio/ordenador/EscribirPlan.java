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
import ontologia.predicados.OrdenadorEstropeadoEscribir;

public class EscribirPlan extends Plan {
    public EscribirPlan() {
    }

    @Override
    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        Escribir content = (Escribir) peticion.getContent();

        Energia energia = content.getEnergia();
        Diversion diversion = content.getDiversion();
        Escritura escritura = content.getEscritura();

        // Disminuye en uno la cantidad de usos restantes hasta el deterioro del ordenador.
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact() - 1;

        if (obsolescencia <= 0) {
            IMessageEvent respuesta = createMessageEvent("ordenador_estropeado_escribir");
            OrdenadorEstropeadoEscribir ordenadorEstropeadoEscribir = new OrdenadorEstropeadoEscribir(energia, diversion, escritura);
            respuesta.setContent(ordenadorEstropeadoEscribir);
            sendMessage(respuesta);
        } else {
            getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia);

            energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
            content.setDiversion(diversion);

            escritura.setExperiencia(escritura.getExperiencia() + Habilidad.HB_NORMAL);
            content.setEscritura(escritura);

            try {
                wait(Accion.TIEMPO_MEDIO);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            IMessageEvent respuesta = createMessageEvent("escritura_realizada");
            respuesta.setContent(content);

            sendMessage(respuesta);

        }


    }
}
