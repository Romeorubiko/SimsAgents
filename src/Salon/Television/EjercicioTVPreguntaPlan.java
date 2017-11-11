package Salon.Television;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.EjercicioTV;
import ontologia.conceptos.habilidades.Deporte;
import ontologia.conceptos.necesidades.*;
import ontologia.predicados.TVEstropeadaEjercicio;

import java.util.ArrayList;

public class EjercicioTVPreguntaPlan extends Plan {
    /**
     * Plan body.
     */
    public void body() {
        IMessageEvent request = (IMessageEvent) getInitialEvent();

        /*
        Se coge la creencia viendo_tv para comprobar si hay sims viendo la TV caso en el que no se podrá hacer ejercicio ya que no se puede
        ver la TV y hacer ejercioio a la vez
         */
        RBeliefbase bb = (RBeliefbase) getBeliefbase();
        RBelief creenciaSimViendoTV = (RBelief) bb.getBelief("viendo_tv");
        Boolean simViendoTV = (Boolean) creenciaSimViendoTV.getFact();

        /*
        Si hay un sim viendo la tv se devuelve un refuse
         */
        if (simViendoTV) {
            IMessageEvent refuse = createMessageEvent("sim_viendo_tv");
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else {
            IMessageEvent agree = createMessageEvent("sim_no_viendo_tv");
            agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            RBelief creenciaObsolescencia = (RBelief) bb.getBelief("obsolescencia_tv");
            Integer obsolescenciaTV = (Integer) creenciaObsolescencia.getFact();
            /*
            Si la tv se encuentra estropeada se devuelve un failure con los recursos y habilidades sin modificar
             */
            if (obsolescenciaTV <= 0) {
                EjercicioTV content = (EjercicioTV) request.getContent();
                Hambre hambre = content.getHambre();
                Higiene higiene = content.getHigiene();
                Diversion diversion = content.getDiversion();
                Energia energia = content.getEnergia();
                Deporte deporte = content.getDeporte();

                IMessageEvent failure = createMessageEvent("tv_estropeada_ejercicio");
                TVEstropeadaEjercicio tvEstropeadaEjercicio = new TVEstropeadaEjercicio(energia, diversion, hambre, higiene, deporte);
                failure.setContent(tvEstropeadaEjercicio);
                failure.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
                sendMessage(failure);

            } else {
                obsolescenciaTV = obsolescenciaTV - 1;
                creenciaObsolescencia.setFact(obsolescenciaTV);

                /*
                Se introduce en la ultimas posición del array de mensajes de hacer ejercicio tv el request del sim
                 */
                RBelief creenciaMensajesEjercicioTV=(RBelief) bb.getBelief("mensajes_ejercicio_tv");
                @SuppressWarnings("unchecked")
                ArrayList<IMessageEvent> arrayMensajesEjercicioTV= (ArrayList<IMessageEvent>)creenciaMensajesEjercicioTV.getFact();
                arrayMensajesEjercicioTV.add(request);
                creenciaMensajesEjercicioTV.setFact(arrayMensajesEjercicioTV);
                /*
                Se introduce en la última posición del array de tiempos de hacer ejercicio tv el tiempo de finalización de la acción
                 */
                RBelief creenciaTiemposEjercicioTV=(RBelief) bb.getBelief("tiempos_fin_ejercicio_tv");
                @SuppressWarnings("unchecked")
                ArrayList<Integer> arrayTiemposEjercicioTV = (ArrayList<Integer>)creenciaTiemposEjercicioTV.getFact();
                RBelief creenciaTiempo=(RBelief) bb.getBelief("tiempo_actual");
                Integer tiempo= (Integer)creenciaTiempo.getFact();
                arrayTiemposEjercicioTV.add(tiempo+ Accion.TIEMPO_MEDIO);
                creenciaTiemposEjercicioTV.setFact(arrayTiemposEjercicioTV);

                RBelief creenciaHaciendoEjercicioTV=(RBelief) bb.getBelief("haciendo_ejercicio_tv");
                Boolean haciendoEjercicioTV= (Boolean) creenciaHaciendoEjercicioTV.getFact();
                /*
                Se dispara el goal en el caso de que sea el primer Sim en usar la TV
                */
                if(!haciendoEjercicioTV){
                    IGoal goal= createGoal("ejercicio_tv_tiempo_superado");
                    dispatchTopLevelGoal(goal);
                }
                creenciaHaciendoEjercicioTV.setFact(true);


            }


        }
    }
}
