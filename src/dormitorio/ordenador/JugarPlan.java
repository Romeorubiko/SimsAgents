package dormitorio.ordenador;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.Chatear;
import ontologia.acciones.JugarVideojuego;
import ontologia.conceptos.Juego;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Logica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import sun.rmi.runtime.Log;

public class JugarPlan extends Plan {
    public JugarPlan() {
    }

    @Override
    public void body() {
        RMessageEvent peticion =((RMessageEvent) getInitialEvent());
        JugarVideojuego content = (JugarVideojuego) peticion.getContent();

        Juego juego = content.getTipo();

        Energia energia = content.getEnergia();
        energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
        content.setEnergia(energia);

        Diversion diversion = content.getDiversion();
        diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
        content.setDiversion(diversion);



        switch (juego.getTipo()) {
            case AJEDREZ:
                Logica logica = content.getLogica();
                logica.setExperiencia(logica.getExperiencia() + Habilidad.HB_NORMAL);
                content.setLogica(logica);
                break;
            default:
                break;

        }

        try {
            wait(Accion.TIEMPO_MEDIO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        IMessageEvent respuesta = createMessageEvent("juego_finalizado");
        respuesta.setContent(content);

        sendMessage(respuesta);
    }
}
