package dormitorio.ordenador;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.NavegarPorInternet;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.OrdenadorEstropeadoNavegarInternet;

public class NavegarPorInternetPlan extends Plan {
    public NavegarPorInternetPlan() {

    }

    @Override
    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        NavegarPorInternet content = (NavegarPorInternet) peticion.getContent();

        Energia energia = content.getEnergia();
        Diversion diversion = content.getDiversion();

        // Disminuye en uno la cantidad de usos restantes hasta el deterioro del ordenador.
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact() - 1;

        if (obsolescencia <= 0) {
            IMessageEvent respuesta = createMessageEvent("ordenador_estropeado_navegar_internet");
            OrdenadorEstropeadoNavegarInternet ordenadorEstropeadoNavegarInternet = new OrdenadorEstropeadoNavegarInternet();
            respuesta.setContent(ordenadorEstropeadoNavegarInternet);
            sendMessage(respuesta);
        } else {
            getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia);

            energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
            content.setDiversion(diversion);

            try {
                wait(Accion.TIEMPO_MEDIO);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            IMessageEvent respuesta = createMessageEvent("navegacion_por_internet_realizada");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
    }
}