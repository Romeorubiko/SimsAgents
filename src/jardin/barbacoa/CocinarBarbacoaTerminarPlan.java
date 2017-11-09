package jardin.barbacoa;
import java.util.Random;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import ontologia.acciones.CocinarComidaBarbacoa;
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

        getGoalbase().getGoal("terminar_cocinar_barbacoa").drop();
        getBeliefbase().getBelief("tiempo_fin_cocinar_barbacoa").setFact(new Integer (0));

        RMessageEvent peticion= (RMessageEvent)getBeliefbase().getBelief("mensaje_cocinar_barbacoa").getFact();
        CocinarComidaBarbacoa contenido = (CocinarComidaBarbacoa)peticion.getContent();

        Higiene h = contenido.getHigiene();
        Hambre hmb = contenido.getHambre();
        Diversion d = contenido.getDiversion();
        Cocina c = contenido.getCocina();


        boolean val = new Random().nextInt(2*c.getExperiencia())==0;

        //Comida quemada. La probabilidad de que se queme la comida es mas pequeña cuanto más alto es el nivel de cocina
        if (val) {
            h.setGrado(h.getGrado()- Necesidad.NC_POCO);
            hmb.setGrado(hmb.getGrado());
            d.setGrado(d.getGrado()+Necesidad.NC_POCO);
            c.setExperiencia(c.getExperiencia()+ Habilidad.HB_NORMAL);

            PerritosQuemados response = new PerritosQuemados(h, hmb, d, c);


            IMessageEvent failure = createMessageEvent("perritos_quemados");
            failure.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            failure.setContent(response);
            sendMessage(failure);
            getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);

        }
        else {
            h.setGrado(h.getGrado()- Necesidad.NC_POCO);
            hmb.setGrado(hmb.getGrado()+Necesidad.NC_NORMAL);
            d.setGrado(d.getGrado()+Necesidad.NC_POCO);
            c.setExperiencia(c.getExperiencia()+ Habilidad.HB_NORMAL);

            HasCocinadoBarbacoa response = new HasCocinadoBarbacoa(h, hmb, d, c);

            IMessageEvent inform = createMessageEvent("has_cocinado_barbacoa");
            inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
            inform.setContent(response);
            sendMessage(inform);
            getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        }

    }
}
