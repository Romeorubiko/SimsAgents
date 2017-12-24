package Salon.EquipoMusica;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.acciones.Reparar;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Mecanica;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasReparado;

public class RepararEquipoRespuestaPlan extends Plan{

    public RepararEquipoRespuestaPlan(){
    }
    
    public void body() {
       /* Se toman las creencias necesarias */
        RBeliefbase bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_equipo_roto");
        IMessageEvent request= (IMessageEvent) creenciaMensaje.getFact();
        RBelief creenciaTiempoFin=(RBelief) bb.getBelief("tiempo_fin_reparar_equipo");
        creenciaTiempoFin.setValue(0);
        getGoalbase().getGoal("reparar_equipo_tiempo_superado").drop();

        Reparar content = (Reparar) request.getContent();

        Higiene higiene= content.getHigiene();
        Energia energia= content.getEnergia();
        Mecanica mecanica= content.getMecanica();

        /* Modificacion de los recursos del Sim */
        higiene.setGrado(content.getHigiene().getGrado()- Necesidad.NC_POCO);
        content.setHigiene(higiene);

        energia.setGrado(content.getEnergia().getGrado()- Necesidad.NC_POCO);
        content.setEnergia(energia);

        mecanica.setExperiencia(content.getMecanica().getExperiencia()+ Habilidad.HB_NORMAL);
        content.setMecanica(mecanica);

        /* Envio del mensaje de que ha reparado el equipo a traves del predicado correspondiente */
        IMessageEvent inform = createMessageEvent("has_reparado_equipo");
        HasReparado hasReparadoEquipo= new HasReparado(energia, higiene, mecanica);
        inform.setContent(hasReparadoEquipo);
        inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
        sendMessage(inform);
        
        /* Actualizacion de la creencia de sim reparando el equipo de musica */
        RBelief creenciaSimReparandoEquipo=(RBelief) bb.getBelief("reparando_equipo");
        creenciaSimReparandoEquipo.setFact(false);

    }
}
