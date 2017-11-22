package dormitorio.estanteria;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.acciones.LeerLibro;
import ontologia.conceptos.Libro;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.LibroLeido;

import java.util.ArrayList;

public class LeerLibroRespuestaPlan extends Plan {

    public LeerLibroRespuestaPlan() {
    }

    @Override
    public void body() {
        ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) getBeliefbase().getBelief("mensajes_estanteria").getFact();
        IMessageEvent peticion = arrayMensajes.remove(0);
        getBeliefbase().getBelief("mensajes_estanteria").setFact(arrayMensajes);

        ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) getBeliefbase().getBelief("tiempos_fin_estanteria").getFact();
        getBeliefbase().getBelief("tiempos_fin_estanteria").setFact(arrayTiempos);

        if (arrayTiempos.isEmpty()) {
            getGoalbase().getGoal("leer_libro_tiempo_superado").drop();
        }

        LeerLibro content = (LeerLibro) peticion.getContent();
        Libro libro = content.getLibro();
        Habilidad habilidad = content.getHabilidad();
        Diversion diversion = content.getDiversion();
        Energia energia = content.getEnergia();

        IMessageEvent respuesta = createMessageEvent("libro_leido");
        respuesta.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());

        diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
        energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);

        switch (libro.getGenero()) {
            case CARISMA:
                Carisma carisma = (Carisma) habilidad;
                carisma.setExperiencia(carisma.getExperiencia() + Habilidad.HB_NORMAL);
                content.setHabilidad(carisma);
                break;
            case COCINA:
                Cocina cocina = (Cocina) habilidad;
                cocina.setExperiencia(cocina.getExperiencia() + Habilidad.HB_NORMAL);
                content.setHabilidad(cocina);
                break;
            case JARDINERIA:
                Jardineria jardineria = (Jardineria) habilidad;
                jardineria.setExperiencia(jardineria.getExperiencia() + Habilidad.HB_NORMAL);
                content.setHabilidad(jardineria);
                break;
            case PINTURA:
                Pintura pintura = (Pintura) habilidad;
                pintura.setExperiencia(pintura.getExperiencia() + Habilidad.HB_NORMAL);
                content.setHabilidad(pintura);
                break;
            case FOTOGRAFIA:
                Fotografia fotografia = (Fotografia) habilidad;
                fotografia.setExperiencia(fotografia.getExperiencia() + Habilidad.HB_NORMAL);
                content.setHabilidad(fotografia);
                break;
            case LOGICA:
                Logica logica = (Logica) habilidad;
                logica.setExperiencia(logica.getExperiencia() + Habilidad.HB_NORMAL);
                content.setHabilidad(logica);
                break;
            case CIENCIA:
                Ciencia ciencia = (Ciencia) habilidad;
                ciencia.setExperiencia(ciencia.getExperiencia() + Habilidad.HB_NORMAL);
                content.setHabilidad(ciencia);
                break;
            default:
                break;
        }

        LibroLeido libroLeido = new LibroLeido(energia, diversion, content.getHabilidad());
        respuesta.setContent(libroLeido);

        sendMessage(respuesta);
    }
}
