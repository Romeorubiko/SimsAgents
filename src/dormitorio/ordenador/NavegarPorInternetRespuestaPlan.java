package dormitorio.ordenador;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.NavegarPorInternet;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.NavegacionPorInternetRealizada;
import ontologia.predicados.OrdenadorEstropeadoNavegarInternet;

public class NavegarPorInternetRespuestaPlan extends Plan {
    public NavegarPorInternetRespuestaPlan() {

    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje").getFact();
        getBeliefbase().getBelief("tiempo_fin").setFact(0);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getGoalbase().getGoal("navegar_internet_tiempo_superado").drop();

        NavegarPorInternet content = (NavegarPorInternet) peticion.getContent();
        Energia energia = content.getEnergia();
        Diversion diversion = content.getDiversion();

        energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
        diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);

        IMessageEvent respuesta = createMessageEvent("navegacion_por_internet_realizada");
        NavegacionPorInternetRealizada navegacionPorInternetRealizada = new NavegacionPorInternetRealizada(energia, diversion);
        respuesta.setContent(navegacionPorInternetRealizada);
        sendMessage(respuesta);
    }
}