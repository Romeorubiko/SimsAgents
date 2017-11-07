package dormitorio.estanteria;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.LeerLibro;
import ontologia.conceptos.Libro;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;
import ontologia.predicados.LibroLeido;
import ontologia.predicados.LibroTerminado;

public class LeerLibroPlan extends Plan {

    public LeerLibroPlan() {
    }

    @Override
    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        LeerLibro content = (LeerLibro) peticion.getContent();

        Libro libro = content.getLibro();
        Habilidad habilidad = content.getHabilidad();
        Diversion diversion = content.getDiversion();
        Energia energia = content.getEnergia();

        int incrementoHabilidad = Habilidad.HB_NORMAL;
        int incrementoDiversion = Necesidad.NC_NORMAL;

        IMessageEvent respuesta = createMessageEvent("libro_leido");
        LibroLeido libroLeido= new LibroLeido(energia,diversion,habilidad);
        respuesta.setContent(libroLeido);

        /*
         * La cantidad de recursos y habilidades modificadas depende del tiempo que se haya estado leyendo el libro, de
         * forma que si el libro se termina antes de que termine la rodaja de tiempo, se modifican menos recursos y
         * habilidades que si hubiera estado toda su rodaja.
         */
        try {
            int millis = 0;
            while (millis < Accion.TIEMPO_MEDIO) {
                wait(1);
                // Si se termina de leer el libro se acaba la rodaja.
                if (millis == libro.getTiempoLectura()) {
                    incrementoHabilidad = Habilidad.HB_POCO;
                    incrementoDiversion = Necesidad.NC_POCO;
                    respuesta = createMessageEvent("libro_terminado");
                    LibroTerminado libroTerminado= new LibroTerminado(energia,diversion,habilidad);
                    respuesta.setContent(libroTerminado);
                    break;
                }
                millis++;
            }
            libro.setTiempoLectura(libro.getTiempoLectura() - millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        diversion.setGrado(content.getDiversion().getGrado() + incrementoDiversion);
        content.setDiversion(diversion);

        energia.setGrado(content.getEnergia().getGrado() - Necesidad.NC_POCO);
        content.setEnergia(energia);

        switch (libro.getGenero()) {
            case CARISMA:
                Carisma carisma = (Carisma) habilidad;
                carisma.setExperiencia(carisma.getExperiencia() + incrementoHabilidad);
                content.setHabilidad(carisma);
                break;
            case COCINA:
                Cocina cocina = (Cocina) habilidad;
                cocina.setExperiencia(cocina.getExperiencia() + incrementoHabilidad);
                content.setHabilidad(cocina);
                break;
            case JARDINERIA:
                Jardineria jardineria = (Jardineria) habilidad;
                jardineria.setExperiencia(jardineria.getExperiencia() + incrementoHabilidad);
                content.setHabilidad(jardineria);
                break;
            case PINTURA:
                Pintura pintura = (Pintura) habilidad;
                pintura.setExperiencia(pintura.getExperiencia() + incrementoHabilidad);
                content.setHabilidad(pintura);
                break;
            case FOTOGRAFIA:
                Fotografia fotografia = (Fotografia) habilidad;
                fotografia.setExperiencia(fotografia.getExperiencia() + incrementoHabilidad);
                content.setHabilidad(fotografia);
                break;
            case LOGICA:
                Logica logica = (Logica) habilidad;
                logica.setExperiencia(logica.getExperiencia() + incrementoHabilidad);
                content.setHabilidad(logica);
                break;
            case CIENCIA:
                Ciencia ciencia = (Ciencia) habilidad;
                ciencia.setExperiencia(ciencia.getExperiencia() + incrementoHabilidad);
                content.setHabilidad(ciencia);
                break;
            default:
                break;
        }

        respuesta.setContent(content);
        sendMessage(respuesta);
    }
}
