package Salon.Television;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.VerTV;
import ontologia.conceptos.CanalTV;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.predicados.TVEstropeada;
import java.util.ArrayList;

public class VerTVPreguntaPlan extends Plan {

    public VerTVPreguntaPlan() {

    }

    /**
     * Plan body.
     */
    public void body() {
        IMessageEvent request = (IMessageEvent) getInitialEvent();

        RBeliefbase bb = (RBeliefbase) getBeliefbase();
        RBelief creenciaHaciendoEjercicioTV=(RBelief) bb.getBelief("haciendo_ejercicio_tv");
        Boolean haciendoEjercicioTV= (Boolean) creenciaHaciendoEjercicioTV.getFact();

        /*
        Si hay un sim haciendo ejercicio en la televisión no se puede verla a la vez
         */
        if(haciendoEjercicioTV){
            IMessageEvent refuse = createMessageEvent("sim_haciendo_ejercicio_tv");
            refuse.setContent(request.getContent());
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        }else{
            IMessageEvent agree = createMessageEvent("sim_no_haciendo_ejercicio_tv");
            agree.setContent(request.getContent());
            agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            RBelief creenciaObsolescencia = (RBelief) bb.getBelief("obsolescencia_tv");
            Integer obsolescenciaTV = (Integer) creenciaObsolescencia.getFact();

            /*
            Si la tv se encuentra estropeada se devuelve un failure con los recursos y habilidades sin modificar
             */
            if (obsolescenciaTV <= 0) {
                VerTV content = (VerTV) request.getContent();
                CanalTV canal = content.getCanalTV();
                Diversion diversion = content.getDiversion();
                Energia energia = content.getEnergia();

                IMessageEvent failure = createMessageEvent("tv_estropeada");
                TVEstropeada tvEstropeada = new TVEstropeada(canal, energia, diversion);
                failure.setContent(tvEstropeada);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(failure);
            }else{
                obsolescenciaTV = obsolescenciaTV - 1;
                creenciaObsolescencia.setFact(obsolescenciaTV);

                /*
                Se introduce en la ultimas posición del array de mensajes de ver la tv el request del sim
                */
                RBelief creenciaMensajes=(RBelief) bb.getBelief("mensajes_ver_tv");
                @SuppressWarnings("unchecked")
                ArrayList<IMessageEvent> arrayMensajes= (ArrayList<IMessageEvent>)creenciaMensajes.getFact();
                arrayMensajes.add(request);
                creenciaMensajes.setFact(arrayMensajes);
                /*
                Se introduce en la última posición del array de tiempos de ver la tv el tiempo de finalización de la acción
                 */
                RBelief creenciaTiemposFinTV=(RBelief) bb.getBelief("tiempos_fin_ver_tv");
                @SuppressWarnings("unchecked")
                ArrayList<Integer> arrayTiempos = (ArrayList<Integer>)creenciaTiemposFinTV.getFact();
                RBelief creenciaTiempo=(RBelief) bb.getBelief("tiempo_actual");
                Integer tiempo= (Integer)creenciaTiempo.getFact();
                arrayTiempos.add(tiempo+ Accion.TIEMPO_MEDIO);
                creenciaTiemposFinTV.setFact(arrayTiempos);

                RBelief creenciaSimViendoTV=(RBelief) bb.getBelief("viendo_tv");
                Boolean simViendoTV= (Boolean) creenciaSimViendoTV.getFact();

                /*
                Se dispara el goal en el caso de que sea el primer Sim en usar la TV
                */
                if(!simViendoTV){
                    IGoal goal= createGoal("ver_tv_tiempo_superado");
                    dispatchTopLevelGoal(goal);
                }
                creenciaSimViendoTV.setFact(true);
            }
        }


    }
}
