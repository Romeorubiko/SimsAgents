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
import ontologia.predicados.JuegoFinalizado;

public class JugarVideojuegoRespuestaPlan extends Plan {
    public JugarVideojuegoRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje").getFact();
        getBeliefbase().getBelief("tiempo_fin").setFact(0);
        getBeliefbase().getBelief("ocupado").setFact(Boolean.FALSE);

        JugarVideojuego content = (JugarVideojuego) peticion.getContent();
        Juego juego = content.getTipo();
        Energia energia = content.getEnergia();
        Diversion diversion = content.getDiversion();
        Logica logica = content.getLogica();

        energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
        diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);

        switch (juego.getTipo()) {
            case AJEDREZ:
                logica.setExperiencia(logica.getExperiencia() + Habilidad.HB_NORMAL);
                break;
            default:
                break;
        }

        IMessageEvent respuesta = createMessageEvent("juego_finalizado");
        JuegoFinalizado juegoFinalizado = new JuegoFinalizado(energia, diversion, logica);
        respuesta.setContent(juegoFinalizado);
        sendMessage(respuesta);
    }
}
