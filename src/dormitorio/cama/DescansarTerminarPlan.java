package dormitorio.cama;
import jadex.runtime.*;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasDescansado;

/**
 * Created by eldgb on 08-Nov-17.
 */
public class DescansarTerminarPlan extends Plan {
    public void body() {
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        //getBeliefbase().getBelief("cama_hecha").setFact(Boolean.FALSE);
        Energia e  = new Energia();
        Integer grado = (Integer)getBeliefbase().getBelief("energia").getFact();
        e.setGrado(grado.intValue()+ Necesidad.NC_MUCHO);
        HasDescansado response = new HasDescansado();
        response.setEnergia(e);
        IMessageEvent respuesta = createMessageEvent("has_descansado");
        respuesta.setContent(response);
        sendMessage(respuesta);
    }

}
