package Salon;

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
    /**
     * Plan body.
     */
    public void body() {
        IMessageEvent request = (IMessageEvent) getInitialEvent();

        /* Se realiza agree siempre ya que la TV nunca se encuentra ocupada*/
        IMessageEvent agree = createMessageEvent("tv_no_ocupada");
        agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
        sendMessage(agree);

		/* Se obtiene la obsolescencia de creencia obsolescencia_tv */
        RBeliefbase bb = (RBeliefbase) getBeliefbase();
        RBelief creenciaObsolescencia = (RBelief) bb.getBelief("obsolescencia_tv");
        Integer obsolescenciaTV = (Integer) creenciaObsolescencia.getFact();

        if (obsolescenciaTV <= 0) {
        /* Mensaje de failure que se le env�a al Sim */

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

            RBelief creenciaMensajes=(RBelief) bb.getBelief("mensajes_tv");
            @SuppressWarnings("unchecked")
            ArrayList<IMessageEvent> arrayMensajes= (ArrayList<IMessageEvent>)creenciaMensajes.getFact();
            RBelief creenciaTiemposFinTV=(RBelief) bb.getBelief("tiempos_fin_tv");
            @SuppressWarnings("unchecked")
            ArrayList<Integer> arrayTiempos = (ArrayList<Integer>)creenciaTiemposFinTV.getFact();

            RBelief creenciaTiempo=(RBelief) bb.getBelief("tiempo_actual");
            Integer tiempo= (Integer)creenciaTiempo.getFact();

            /*
            Se actualiza el Array de Mensajes añadiendo el mensaje del sim actual en la ultima posicion
             */
            arrayMensajes.add(request);
            creenciaMensajes.setFact(arrayMensajes);



            /*
            Se actualiza el array de tiempos de finalización de las TV añadiendo la ultima
             */
            arrayTiempos.add(tiempo+ Accion.TIEMPO_MEDIO);
            creenciaTiemposFinTV.setFact(arrayTiempos);

             /*
            Se obtiene la creencia  que indica que un sim esta viendo la TV
             */
            RBelief creenciaSimViendoTV=(RBelief) bb.getBelief("viendo_tv");
            Boolean simViendoTV= (Boolean) creenciaSimViendoTV.getFact();
            RBelief creenciaHaciendoEjercicioTV=(RBelief) bb.getBelief("viendo_tv");
            Boolean haciendoEjercicioTV= (Boolean) creenciaSimViendoTV.getFact();


            if(!simViendoTV && !haciendoEjercicioTV){
                IGoal goal= createGoal("ver_tv_tiempo_superado");
                dispatchTopLevelGoal(goal);
            }
            creenciaSimViendoTV.setFact(true);
        }
    }
}
