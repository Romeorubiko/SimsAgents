package dormitorio.ordenador;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.acciones.RepararOrdenador;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Mecanica;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.OrdenadorReparado;

public class RepararOrdenadorRespuestaPlan extends Plan {

    public RepararOrdenadorRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje").getFact();
        getBeliefbase().getBelief("tiempo_fin").setFact(0);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getGoalbase().getGoal("reparar_ordenador_tiempo_superado").drop();

        RepararOrdenador content = (RepararOrdenador) peticion.getContent();
        Energia energia = content.getEnergia();
        Higiene higiene = content.getHigiene();
        Mecanica mecanica = content.getMecanica();

        energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
        higiene.setGrado(higiene.getGrado() - Necesidad.NC_POCO);
        mecanica.setExperiencia(mecanica.getExperiencia() + Habilidad.HB_NORMAL);

        IMessageEvent respuesta = createMessageEvent("ordenador_reparado");
        respuesta.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());

        OrdenadorReparado ordenadorReparado = new OrdenadorReparado(energia, higiene, mecanica);
        respuesta.setContent(ordenadorReparado);
        sendMessage(respuesta);
    }
}
