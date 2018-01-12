/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */

package jardin.barbacoa;
import java.util.Random;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.CocinarComidaBarbacoa;
import ontologia.conceptos.habilidades.Cocina;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasCocinadoBarbacoa;
import ontologia.predicados.PerritosQuemados;


public class CocinarBarbacoaTerminarPlan extends Plan {
    public void body() {

        int new_timer = (int) (System.currentTimeMillis()/1000 + 10000000);
      
        IMessageEvent peticion= (IMessageEvent)getBeliefbase().getBelief("mensaje_cocinar_barbacoa").getFact();
        CocinarComidaBarbacoa contenido = (CocinarComidaBarbacoa)peticion.getContent();

        Higiene h = contenido.getHigiene();
        Hambre hmb = contenido.getHambre();
        Diversion d = contenido.getDiversion();
        Cocina c = contenido.getCocina();

        int nivel = c.getNivel();
        boolean val = new Random().nextInt(2*nivel)==0;

        //Comida quemada. La probabilidad de que se queme la comida es mas pequeña cuanto más alto es el nivel de cocina
        if (val) {
			
		    h.setGrado(h.getGrado()- Necesidad.NC_POCO);
		    //hmb.setGrado(hmb.getGrado());
		    d.setGrado(d.getGrado()+Necesidad.NC_POCO);
		    c.setExperiencia(c.getExperiencia()+ Habilidad.HB_NORMAL);
		    PerritosQuemados response = new PerritosQuemados(h, hmb, d, c);
		    IMessageEvent failure = createMessageEvent("perritos_quemados");
		    failure.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
		    failure.setContent(response);
		    sendMessage(failure);
        }

        else {
        
            h.setGrado(h.getGrado()- Necesidad.NC_POCO);
            //hmb.setGrado(hmb.getGrado()+Necesidad.NC_MUCHO);
            hmb.setGrado(100);
            d.setGrado(d.getGrado()+Necesidad.NC_POCO);
            c.setExperiencia(c.getExperiencia()+ Habilidad.HB_NORMAL);

            HasCocinadoBarbacoa response = new HasCocinadoBarbacoa(h, hmb, d, c);


            IMessageEvent inform = createMessageEvent("has_cocinado_barbacoa");

            inform.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameter(SFipa.SENDER).getValue());
   
           
            inform.setContent(response);
            sendMessage(inform);
            
        }  
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);
        getBeliefbase().getBelief("tiempo_fin_cocinar_barbacoa").setFact(new Integer(new_timer));

    }
}
