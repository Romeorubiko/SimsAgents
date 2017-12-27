package Salon.Television;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.acciones.VerTV;
import ontologia.conceptos.CanalTV;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasVistoTV;

import java.util.ArrayList;

public class VerTVRespuestaPlan extends Plan {

    public VerTVRespuestaPlan() {

    }

    /**
     *  Plan body.
     */
    public void body()
    {


		/* Se coge el mensaje de la primera posicion del array de mensajes guardado en la creencia mensajes_tv */
        RBeliefbase bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaMensajesVerTV=(RBelief) bb.getBelief("mensajes_ver_tv");
        @SuppressWarnings("unchecked")
        ArrayList<IMessageEvent> arrayMensajesVerTV= (ArrayList<IMessageEvent>) creenciaMensajesVerTV.getFact();
        IMessageEvent request = arrayMensajesVerTV.get(0);
        /*
        Se borra el primer mensaje del Array de Mensajes de la creencia mensajes_tv,
        ya que es el primero en activar este plan
         */
        arrayMensajesVerTV.remove(0);
        creenciaMensajesVerTV.setFact(arrayMensajesVerTV);

        /*
        Se borra la primera posición del Array de tiempos de finalización de la creencia tiempos_fin_tv
         */
        RBelief creenciaTiempoEjercicioTV=(RBelief) bb.getBelief("tiempos_fin_ver_tv");
        @SuppressWarnings("unchecked")
        ArrayList<Integer> arrayTiemposEjercicioTV = (ArrayList<Integer>) creenciaTiempoEjercicioTV.getFact();
        arrayTiemposEjercicioTV.remove(0);
        creenciaTiempoEjercicioTV.setValue(arrayTiemposEjercicioTV);

        /*
        Si la tv no esta ocupado se termina el goal ya que no hay que comprabar la condición indicada en maintaincondition
        ya que no hay ningún sim utilizandolo
        Si estuviera ocuapada no habría que terminar el goal ya que habría que se actualizaría los valores de la condicion
        con los datos del siguiente Sim. Volviendose a meter en este plan para comprobar devolverle un inform cuando termine de
        ver la TV
         */
        if(arrayTiemposEjercicioTV.isEmpty()) {
            getGoalbase().getGoal("ver_tv_tiempo_superado").drop();
            RBelief creenciaSimViendoTV=(RBelief) bb.getBelief("viendo_tv");
            creenciaSimViendoTV.setFact(false);
        }

        VerTV content = (VerTV)request.getContent();
        CanalTV canal= content.getCanalTV();
        Diversion diversion= content.getDiversion();
        Energia energia= content.getEnergia();


        /*
         * Energia
         */
        energia.setGrado(content.getEnergia().getGrado()- Necesidad.NC_POCO);
        content.setEnergia(energia);

        /*
         * Diversion
         */
        diversion.setGrado(content.getDiversion().getGrado()+ Necesidad.NC_NORMAL);
        content.setDiversion(diversion);

        /*
         * Se modifican las habilidades Carisma o Cocina dependiendo del canal que solicie el Sim
         */
        if(canal.getCanal().equals(CanalTV.Canales.COCINA)){
            canal.getCocina().setExperiencia(content.getCanalTV().getCocina().getExperiencia()+ Habilidad.HB_NORMAL);
            content.getCanalTV().setCocina(canal.getCocina());
        } else{
            canal.getCarisma().setExperiencia(content.getCanalTV().getCarisma().getExperiencia()+Habilidad.HB_NORMAL);
            content.getCanalTV().setCarisma(canal.getCarisma());
        }

        IMessageEvent inform = createMessageEvent("has_visto_tv");
        HasVistoTV hasvistoTV= new HasVistoTV(canal,energia,diversion);
        inform.setContent(hasvistoTV);
        inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
        sendMessage(inform);
    }
}


