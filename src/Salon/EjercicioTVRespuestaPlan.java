package Salon;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.acciones.EjercicioTV;
import ontologia.conceptos.habilidades.Deporte;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.*;
import ontologia.predicados.HasHechoEjercicioTV;

import java.util.ArrayList;

public class EjercicioTVRespuestaPlan extends Plan{
    /**
     *  Plan body.
     */
    public void body()
    {
        /* Se coge y se borra el mensaje de la primera posicion del array de mensajes de ejercicio tv en la creencia mensajes_tv */
        RBeliefbase bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaMensajesEjercicioTV=(RBelief) bb.getBelief("mensajes_ejercicio_tv");
        @SuppressWarnings("unchecked")
        ArrayList<IMessageEvent> arrayMensajesEjercicioTV= (ArrayList<IMessageEvent>) creenciaMensajesEjercicioTV.getFact();
        IMessageEvent request = arrayMensajesEjercicioTV.get(0);

        arrayMensajesEjercicioTV.remove(0);
        creenciaMensajesEjercicioTV.setFact(arrayMensajesEjercicioTV);

        /*
        Se borra la primera posición del Array de tiempos de finalización de la creencia tiempos_fin_tv
         */
        RBelief creenciaTiempoEjercicioTV=(RBelief) bb.getBelief("tiempos_fin_ejercicio_tv");
        @SuppressWarnings("unchecked")
        ArrayList<Integer> arrayTiemposEjercicioTV = (ArrayList<Integer>) creenciaTiempoEjercicioTV.getFact();
        arrayTiemposEjercicioTV.remove(0);
        creenciaTiempoEjercicioTV.setValue(arrayTiemposEjercicioTV);

        /*
        Si la tv no esta ocupado se termina el goal ya que no hay que comprabar la condición indicada en maintaincondition
        ya que no hay ningún sim utilizandola
        Si estuviera ocuapada no habría que terminar el goal ya que habría que se actualizaría los valores de la condicion
        con los datos del siguiente Sim. Volviendose a meter en este plan para devolverle un inform cuando termine de
        ver la TV
         */
        if(arrayTiemposEjercicioTV.isEmpty()) {
            getGoalbase().getGoal("ejercicio_tv_tiempo_superado").drop();
            RBelief haciendoEjercicioTV=(RBelief) bb.getBelief("viendo_tv");
            haciendoEjercicioTV.setFact(false);
        }

        EjercicioTV content = (EjercicioTV)request.getContent();
        Hambre hambre= content.getHambre();
        Higiene higiene= content.getHigiene();
        Diversion diversion= content.getDiversion();
        Energia energia= content.getEnergia();
        Deporte deporte= content.getDeporte();

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
		 * Higiene
		 */
        higiene.setGrado(content.getHigiene().getGrado()- Necesidad.NC_NORMAL);
        content.setHigiene(higiene);

		/*
		 * Hambre
		 */
        hambre.setGrado(content.getHambre().getGrado()- Necesidad.NC_NORMAL);
        content.setHambre(hambre);

		/*
		 * Deporte
		 */
        deporte.setExperiencia(content.getDeporte().getExperiencia()+ Habilidad.HB_POCO);
        content.setDeporte(deporte);

        IMessageEvent inform = createMessageEvent("has_hecho_ejercicio_tv");
        HasHechoEjercicioTV hasHechoEjercicioTV= new HasHechoEjercicioTV(energia,diversion,hambre,higiene,deporte);
        inform.setContent(hasHechoEjercicioTV);
        inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
        sendMessage(inform);

    }
}
