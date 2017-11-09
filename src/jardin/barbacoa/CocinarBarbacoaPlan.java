package jardin.barbacoa;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.CocinarComidaBarbacoa;
import ontologia.conceptos.habilidades.Cocina;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Hambre;
import ontologia.conceptos.necesidades.Higiene;
import ontologia.predicados.BarbacoaRota;

/**
 * Created by eldgb on 09-Nov-17.
 */
public class CocinarBarbacoaPlan extends Plan {
    public void body() {
        RMessageEvent peticion = ((RMessageEvent)getInitialEvent());
        CocinarComidaBarbacoa content = (CocinarComidaBarbacoa) peticion.getContent();
        Hambre hmb = content.getHambre();
        Higiene h = content.getHigiene();
        Diversion d = content.getDiversion();
        Cocina c = content.getCocina();
        Boolean ocupado = (Boolean)getBeliefbase().getBelief("ocupado").getFact();
        Boolean estropeado = (Boolean)getBeliefbase().getBelief("estropeado").getFact();

        if(ocupado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("barbacoa_ocupada_cocinar");
            respuesta.setContent(content);
            sendMessage(respuesta);
        }
        else if (estropeado.booleanValue()) {
            IMessageEvent respuesta = createMessageEvent("barbacoa_estropeada");
            BarbacoaRota response = new BarbacoaRota();
            response.setDiversion(d);
            response.setHambre(hmb);
            response.setHigiene(h);
            response.setCocina(c);
            sendMessage(respuesta);
        }
        else if (((Integer) getBeliefbase().getBelief("obsolescencia").getFact()).intValue() - 1<=0){
            getBeliefbase().getBelief("estropeado").setFact(Boolean.TRUE);
            IMessageEvent respuesta = createMessageEvent("barbacoa_estropeada");
            BarbacoaRota response = new BarbacoaRota();
            response.setDiversion(d);
            response.setHambre(hmb);
            response.setHigiene(h);
            response.setCocina(c);
            sendMessage(respuesta);
        }
        else {
            getBeliefbase().getBelief("ocupado").setFact(Boolean.TRUE);
            int obsolescencia = ((Integer) getBeliefbase().getBelief("obsolescencia").getFact()).intValue() - 1;
            getBeliefbase().getBelief("obsolescencia").setFact(new Integer (obsolescencia));
            int grado_hmb = content.getHambre().getGrado();
            int grado_h = content.getHigiene().getGrado();
            int grado_d = content.getDiversion().getGrado();
            int experiencia_c = content.getCocina().getExperiencia();
            int nivel_c = content.getCocina().getNivel();
            int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_MEDIO;

            getBeliefbase().getBelief("hambre").setFact(new Integer(grado_hmb));
            getBeliefbase().getBelief("higiene").setFact(new Integer(grado_h));
            getBeliefbase().getBelief("diversion").setFact(new Integer(grado_d));
            getBeliefbase().getBelief("experiencia_cocina").setFact(new Integer(experiencia_c));
            getBeliefbase().getBelief("nivel_cocina").setFact(new Integer(nivel_c));
            getBeliefbase().getBelief("tiempoFinalizacion").setFact(new Integer(end_timer));

        }

    }
}
