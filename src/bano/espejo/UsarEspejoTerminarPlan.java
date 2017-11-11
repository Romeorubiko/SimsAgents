package bano.espejo;

import ontologia.conceptos.habilidades.Carisma;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.EspejoUsado;

import jadex.runtime.*;


public class UsarEspejoTerminarPlan extends Plan {

    public void body(){
    	
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        Carisma carisma = new Carisma();
        Energia energia = new Energia();
        
        Integer experiencia_carisma = (Integer)getBeliefbase().getBelief("experiencia_carisma").getFact();
        Integer grado_energia = (Integer)getBeliefbase().getBelief("energia").getFact();
       
        carisma.setExperiencia(experiencia_carisma.intValue()+ Habilidad.HB_NORMAL);
        energia.setGrado(grado_energia.intValue()+ Necesidad.NC_POCO);
        
        EspejoUsado response = new EspejoUsado();
        response.setCarisma(carisma);
        response.setEnergia(energia);
 

        IMessageEvent respuesta = createMessageEvent("espejo_usado");
        respuesta.setContent(response);
        sendMessage(respuesta);
}
}