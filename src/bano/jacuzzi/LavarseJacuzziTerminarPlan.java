package bano.jacuzzi;

import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.TeHasLavado;

import jadex.runtime.*;


public class LavarseJacuzziTerminarPlan extends Plan {

    public void body(){
    	 getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
         getBeliefbase().getBelief("estropeado").setFact(Boolean.FALSE);
         getBeliefbase().getBelief("obsolescencia").setFact(100);
         Higiene higiene = new Higiene();
         Energia energia = new Energia();
        
         Integer grado_higiene = (Integer)getBeliefbase().getBelief("higiene").getFact();
         Integer grado_energia = (Integer)getBeliefbase().getBelief("energia").getFact();
        
         higiene.setGrado(grado_higiene- Necesidad.NC_NORMAL);
         energia.setGrado(grado_energia+ Necesidad.NC_POCO);
         
         TeHasLavado response = new TeHasLavado();
         response.setHigiene(higiene);
         response.setEnergia(energia);

         IMessageEvent respuesta = createMessageEvent("te_has_lavado");
         respuesta.setContent(response);
         sendMessage(respuesta);
 }
 }