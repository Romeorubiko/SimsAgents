package bano.jacuzzi;

import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasCompartidoJacuzzi;

import jadex.runtime.*;


public class CompartirJacuzziTerminarPlan extends Plan {

    public void body(){
    	
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("estropeado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("obsolescencia").setFact(100);
        InteraccionSocial interaccionsocial = new InteraccionSocial();
        Energia energia = new Energia();
         
        Integer grado_interaccionsocial = (Integer)getBeliefbase().getBelief("interaccionsocial").getFact();
        Integer grado_energia = (Integer)getBeliefbase().getBelief("energia").getFact();
       
        interaccionsocial.setGrado(grado_interaccionsocial- Necesidad.NC_NORMAL);
        energia.setGrado(grado_energia+ Necesidad.NC_POCO);
        
        HasCompartidoJacuzzi response = new HasCompartidoJacuzzi();
        response.setInteraccionSocial(interaccionsocial);
        response.setEnergia(energia);

        IMessageEvent respuesta = createMessageEvent("has_compartido_jacuzzi");
        respuesta.setContent(response);
        sendMessage(respuesta);
}
}