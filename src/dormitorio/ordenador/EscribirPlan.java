package dormitorio.ordenador;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.Escribir;
import ontologia.conceptos.habilidades.Escritura;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;

public class EscribirPlan extends Plan {
    public EscribirPlan() {
    }

    @Override
    public void body() {
        RMessageEvent peticion =((RMessageEvent) getInitialEvent());
        Escribir content = (Escribir) peticion.getContent();

        Energia energia = content.getEnergia();
        energia.setGrado(energia.getGrado() - Necesidad.NC_POCO);
        content.setEnergia(energia);


        Diversion diversion = content.getDiversion();
        diversion.setGrado(diversion.getGrado() + Necesidad.NC_NORMAL);
        content.setDiversion(diversion);

        Escritura escritura = content.getEscritura();
        escritura.setExperiencia(escritura.getExperiencia()+ Habilidad.HB_NORMAL);
        content.setEscritura(escritura);

        try {
            wait(Accion.TIEMPO_MEDIO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        IMessageEvent respuesta = createMessageEvent("escritura_realizada");
        respuesta.setContent(content);

        sendMessage(respuesta);
    }
}
