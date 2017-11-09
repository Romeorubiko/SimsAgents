package jardin.barbacoa;
import java.util.Random;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.conceptos.habilidades.Cocina;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasCocinadoBarbacoa;
import ontologia.predicados.PerritosQuemados;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class CocinarBarbacoaTerminarPlan extends Plan {
    public void body() {

        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);

        Higiene h = new Higiene();
        Hambre hmb = new Hambre();
        Diversion d = new Diversion();
        Cocina c = new Cocina();

        Integer grado_h = (Integer)getBeliefbase().getBelief("higiene").getFact();
        Integer grado_hmb = (Integer)getBeliefbase().getBelief("hambre").getFact();
        Integer grado_d = (Integer)getBeliefbase().getBelief("diversion").getFact();
        Integer experiencia_c = (Integer)getBeliefbase().getBelief("experiencia_cocina").getFact();
        Integer nivel_c = (Integer)getBeliefbase().getBelief("nivel_cocina").getFact();

        boolean val = new Random().nextInt(2*nivel_c.intValue())==0;

        //Comida quemada. La probabilidad de que se queme la comida es mas pequeña cuanto más alto es el nivel de cocina
        if (val) {
            h.setGrado(grado_h.intValue()- Necesidad.NC_POCO);
            hmb.setGrado(grado_hmb.intValue());
            d.setGrado(grado_d.intValue()+Necesidad.NC_POCO);
            c.setExperiencia(experiencia_c.intValue()+ Habilidad.HB_NORMAL);

            PerritosQuemados response = new PerritosQuemados();
            response.setCocina(c);
            response.setDiversion(d);
            response.setHambre(hmb);
            response.setHigiene(h);

            IMessageEvent respuesta = createMessageEvent("perritos_quemados");
            respuesta.setContent(response);
            sendMessage(respuesta);

        }
        else {
            h.setGrado(grado_h.intValue()- Necesidad.NC_POCO);
            hmb.setGrado(grado_hmb.intValue()+Necesidad.NC_NORMAL);
            d.setGrado(grado_d.intValue()+Necesidad.NC_POCO);
            c.setExperiencia(experiencia_c.intValue()+ Habilidad.HB_NORMAL);

            HasCocinadoBarbacoa response = new HasCocinadoBarbacoa();
            response.setCocina(c);
            response.setDiversion(d);
            response.setHambre(hmb);
            response.setHigiene(h);

            IMessageEvent respuesta = createMessageEvent("has_cocinado_barbacoa");
            respuesta.setContent(response);
            sendMessage(respuesta);
        }

    }
}
