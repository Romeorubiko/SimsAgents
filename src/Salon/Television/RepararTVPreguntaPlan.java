package Salon.Television;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;

public class RepararTVPreguntaPlan extends Plan{

    public RepararTVPreguntaPlan() {
    }

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
        RBelief creenciaObsolescencia = (RBelief) bb.getBelief("obsolescencia_tv");
        Integer obsolescenciaTV = (Integer) creenciaObsolescencia.getFact();
        RBelief creenciaHaciendoEjercicioTV=(RBelief) bb.getBelief("haciendo_ejercicio_tv");
        Boolean haciendoEjercicioTV= (Boolean) creenciaHaciendoEjercicioTV.getFact();
        RBelief creenciaSimReparandoTV=(RBelief) bb.getBelief("reparando_tv");
        Boolean simReparandoTV= (Boolean) creenciaSimReparandoTV.getFact();

        if(simReparandoTV){
            IMessageEvent refuse = createMessageEvent("tv_ocupada");
            refuse.setContent(request.getContent());
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else if(obsolescenciaTV>0){
            IMessageEvent refuse = createMessageEvent("tv_no_estropeada");
            refuse.setContent(request.getContent());
            refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(refuse);
        } else{
            simReparandoTV=true;
            creenciaSimReparandoTV.setFact(simReparandoTV);

            RBelief creenciaTiempoFinRepararTV=(RBelief) bb.getBelief("tiempo_fin_reparar_tv");
            RBelief creenciaTiempo=(RBelief) bb.getBelief("tiempo_actual");
            RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_reparar_tv");

            Integer tiempo= (Integer)creenciaTiempo.getFact();
            creenciaTiempoFinRepararTV.setFact(tiempo + Accion.TIEMPO_CORTO);
            creenciaMensaje.setFact(request);

            IMessageEvent agree = createMessageEvent("tv_libre");
            agree.setContent(request.getContent());
            agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(agree);

            IGoal goal= createGoal("leer_periodico_tiempo_superado");
            dispatchTopLevelGoal(goal);






        }
    }

}
