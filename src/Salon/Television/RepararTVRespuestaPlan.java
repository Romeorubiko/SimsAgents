package Salon.Television;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.acciones.RepararTv;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Mecanica;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasReparadoTV;

public class RepararTVRespuestaPlan extends Plan{

    public RepararTVRespuestaPlan(){
    }

    /**
     * Plan body.
     */
    public void body() {
       /* Se coge el mensaje de la primera posicion del array de mensajes guardado en la creencia mensajes_tv */
        RBeliefbase bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaSimReparandoTV=(RBelief) bb.getBelief("reparando_tv");
        RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_reparar_tv");
        IMessageEvent request= (IMessageEvent) creenciaMensaje.getFact();

        RBelief creenciaTiempoRepararTV=(RBelief) bb.getBelief("tiempo_fin_reparar_tv");
        creenciaTiempoRepararTV.setValue(0);
        getGoalbase().getGoal("reparar_tv_tiempo_superado").drop();

        RepararTv content = (RepararTv) request.getContent();

        Higiene higiene= content.getHigiene();
        Energia energia= content.getEnergia();
        Mecanica mecanica= content.getMecanica();

        /*
		 * Higiene
		 */
        higiene.setGrado(content.getHigiene().getGrado()- Necesidad.NC_POCO);
        content.setHigiene(higiene);

        /*
         * Energía
         */
        energia.setGrado(content.getEnergia().getGrado()- Necesidad.NC_POCO);
        content.setEnergia(energia);

        /*
         * Lógica
         */
        mecanica.setExperiencia(content.getMecanica().getExperiencia()+ Habilidad.HB_NORMAL);
        content.setMecanica(mecanica);

        IMessageEvent inform = createMessageEvent("has_reparado_tv");
        HasReparadoTV hasReparadoTV= new HasReparadoTV(higiene,energia,mecanica);
        inform.setContent(hasReparadoTV);
        inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
        sendMessage(inform);
        creenciaSimReparandoTV.setFact(false);

    }
}
