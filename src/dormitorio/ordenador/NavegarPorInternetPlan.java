package dormitorio.ordenador;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.NavegarPorInternet;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;

public class NavegarPorInternetPlan extends Plan {
    public NavegarPorInternetPlan() {

    }

    @Override
    public void body() {
            RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
            NavegarPorInternet content = (NavegarPorInternet) peticion.getContent();

            Energia energia = content.getEnergia();
            energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            Diversion diversion = content.getDiversion();
            diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
            content.setDiversion(diversion);

            try {
                wait(Accion.TIEMPO_MEDIO);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            IMessageEvent respuesta = createMessageEvent("Navegacion por internet realizada");
            respuesta.setContent(content);
            sendMessage(respuesta);

    }
}