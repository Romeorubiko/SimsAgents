package dormitorio.ordenador;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.JugarVideojuego;
import ontologia.conceptos.Juego;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Logica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.AparatoEstropeadoJugar;

public class JugarVideojuegoPlan extends Plan {
    public JugarVideojuegoPlan() {
    }

    @Override
    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        JugarVideojuego content = (JugarVideojuego) peticion.getContent();

        Juego juego = content.getTipo();
        Energia energia = content.getEnergia();
        Diversion diversion = content.getDiversion();
        Logica logica = content.getLogica();

        // Disminuye en uno la cantidad de usos restantes hasta el deterioro del ordenador.
        Integer obsolescencia = (Integer) getBeliefbase().getBelief("obsolescencia").getFact() - 1;

        if (obsolescencia <= 0) {
            IMessageEvent respuesta = createMessageEvent("aparato_estropeado");
            AparatoEstropeadoJugar aparatoEstropeadoJugar = new AparatoEstropeadoJugar(energia, diversion, logica);
            respuesta.setContent(aparatoEstropeadoJugar);
            sendMessage(respuesta);
        } else {
            getBeliefbase().getBelief("obsolescencia").setFact(obsolescencia);

            energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
            content.setEnergia(energia);

            diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
            content.setDiversion(diversion);

            switch (juego.getTipo()) {
                case AJEDREZ:
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
}
