package dormitorio.estanteria;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.LeerLibro;
import ontologia.conceptos.Libro;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

public class LeerLibroPlan extends Plan {
    @Override
    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        LeerLibro content = (LeerLibro) peticion.getContent();

        Libro libro = content.getLibro();

        Diversion diversion = content.getDiversion();
        diversion.setGrado(content.getDiversion().getGrado() + Necesidad.NC_NORMAL);
        content.setDiversion(diversion);

        Energia energia = content.getEnergia();
        energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
        content.setEnergia(energia);

        Habilidad habilidad = content.getHabilidad();

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

        try {
            wait(Accion.TIEMPO_LARGO);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        IMessageEvent respuesta = createMessageEvent("libro_leido");
        respuesta.setContent(content);
    }
}
