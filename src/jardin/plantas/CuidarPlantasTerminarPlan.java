package jardin.plantas;
import ontologia.acciones.*;
import ontologia.predicados.*;
import ontologia.conceptos.habilidades.Cocina;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Jardineria;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;

import java.util.*;

import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;


public class CuidarPlantasTerminarPlan extends Plan {

    public void body(){
    	 getGoalbase().getGoal("terminar_cuidar_plantas").drop();
         getBeliefbase().getBelief("tiempo_fin_cuidar_plantas").setFact(new Integer (0));
         RMessageEvent peticion= (RMessageEvent)getBeliefbase().getBelief("mensaje_cuidar_plantas").getFact();
         CuidarPlantas contenido = (CuidarPlantas) peticion.getContent();
    	

         Higiene h = contenido.getHigiene();
         Energia e = contenido.getEnergia();
         Jardineria j = contenido.getJardineria();

             h.setGrado(h.getGrado()- Necesidad.NC_POCO);
             e.setGrado(e.getGrado()-Necesidad.NC_NORMAL);
             j.setExperiencia(j.getExperiencia()+ Habilidad.HB_NORMAL);

             HasCuidadoPlantas response = new HasCuidadoPlantas();
             response.setJardineria(j);
             response.setEnergia(e);
             response.setHigiene(h);

             IMessageEvent respuesta = createMessageEvent("has_cuidado_plantas");
             respuesta.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
             respuesta.setContent(response);
             sendMessage(respuesta);
             getBeliefbase().getBelief("estoy_libre").setFact(Boolean.TRUE);
    }
}