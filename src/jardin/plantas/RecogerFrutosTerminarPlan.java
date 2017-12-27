package jardin.plantas;
import ontologia.acciones.*;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Jardineria;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.*;

import java.util.*;

import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;


public class RecogerFrutosTerminarPlan extends Plan {

    public void body(){
    	 getGoalbase().getGoal("terminar_recoger_frutos").drop();
         getBeliefbase().getBelief("tiempo_fin_recoger_frutos").setFact(new Integer (0));
         RMessageEvent peticion= (RMessageEvent)getBeliefbase().getBelief("mensaje_recoger_frutos").getFact();
         RecogerFrutos contenido = (RecogerFrutos) peticion.getContent();
     	

         Hambre h = contenido.getHambre();
         Energia e = contenido.getEnergia();
         Jardineria j = contenido.getJardineria();


             h.setGrado(h.getGrado()+ Necesidad.NC_POCO);
             e.setGrado(e.getGrado()-Necesidad.NC_POCO);
             j.setExperiencia(j.getExperiencia()+ Habilidad.HB_POCO);

             HasRecogidoFrutos response = new HasRecogidoFrutos();
             response.setJardineria(j);
             response.setEnergia(e);
             response.setHambre(h);

             IMessageEvent respuesta = createMessageEvent("has_recogido_frutos");
             respuesta.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
             respuesta.setContent(response);
             sendMessage(respuesta);
             getBeliefbase().getBelief("estoy_libre").setFact(Boolean.TRUE);
    }
}
