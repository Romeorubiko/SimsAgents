/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */


package sala_recreativa.maquina_ejercicios;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.acciones.Entrenar;
import ontologia.conceptos.habilidades.Deporte;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasEntrenado;


public class EntrenarTerminarPlan extends Plan {
    public void body() {

        //getGoalbase().getGoal("terminar_entrenar").drop();
    	int new_timer = (int) (System.currentTimeMillis() + 100000);
        getBeliefbase().getBelief("tiempo_fin_entrenar").setFact(new Integer (new_timer));
        RMessageEvent peticion= (RMessageEvent)getBeliefbase().getBelief("mensaje_entrenar").getFact();
        Entrenar contenido = (Entrenar) peticion.getContent();


        Energia e = contenido.getEnergia();
        Higiene h = contenido.getHigiene();
        Hambre hmb = contenido.getHambre();
        Deporte d = contenido.getDeporte();

        //A más nivel gasta menos energía
        e.setGrado(e.getGrado()- Necesidad.NC_POCO/d.getNivel());
        h.setGrado(h.getGrado()-Necesidad.NC_POCO);
        hmb.setGrado(hmb.getGrado()-Necesidad.NC_POCO);
        d.setExperiencia(d.getExperiencia()+ Habilidad.HB_MUCHO);

        HasEntrenado response = new HasEntrenado(e, h, hmb, d);

        IMessageEvent inform = createMessageEvent("has_entrenado");
        inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
        inform.setContent(response);
        sendMessage(inform);

        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
    }
}
