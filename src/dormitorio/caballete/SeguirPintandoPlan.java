package dormitorio.caballete;

import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;
import ontologia.acciones.SeguirPintando;
import ontologia.conceptos.Cuadro;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Pintura;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;

public class SeguirPintandoPlan extends Plan {
    private SeguirPintando content;
    private Diversion diversion;
    private Energia energia;
    private Pintura pintura;

    @Override
    public void body() {
        RMessageEvent peticion = ((RMessageEvent) getInitialEvent());
        content = (SeguirPintando) peticion.getContent();

        Cuadro cuadro = (Cuadro) getBeliefbase().getBelief("cuadro_instalado").getFact();

        if (cuadro == null) {
            System.out.println("No hay un cuadro instalado en el caballete.");
        } else {
            diversion = content.getDiversion();
            energia = content.getEnergia();
            pintura = content.getPintura();

            int incrementoPintura = Habilidad.HB_MUCHO;
            int incrementoRecurso = Necesidad.NC_NORMAL;

            IMessageEvent respuesta = createMessageEvent("cuadro_pintado");

            try {
                int millis = 0;
                while (millis < Accion.TIEMPO_MEDIO) {
                    wait(1);
                    // Si se termina de pintar el cuadro se acaba la rodaja.
                    if (millis == cuadro.getTiempoPintado()) {
                        incrementoPintura = Habilidad.HB_POCO;
                        incrementoRecurso = Necesidad.NC_POCO;
                        respuesta = createMessageEvent("cuadro_terminado");
                        break;
                    }
                    millis++;
                }
                cuadro.setTiempoPintado(cuadro.getTiempoPintado() - millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (cuadro.getArte()) {
                case POP:
                    if (pintura.getNivel() > 2) {
                        modificarRecursos(incrementoPintura, incrementoRecurso);
                    } else {
                        System.out.println("No tiene suficiente nivel de Pintura para realizar un cuadro pop.");
                    }
                    break;
                case REALISTA:
                    if (pintura.getNivel() > 3) {
                        modificarRecursos(incrementoPintura, incrementoRecurso);
                    }
                    System.out.println("No tiene suficiente nivel de Pintura para realizar un cuadro realista.");
                    break;
                case ABSTRACTO:
                    if (pintura.getNivel() > 3) {
                        modificarRecursos(incrementoPintura, incrementoRecurso);
                    }
                    System.out.println("No tiene suficiente nivel de Pintura para realizar un cuadro abstracto.");
                    break;
                case CLASICO:
                    if (pintura.getNivel() > 4) {
                        modificarRecursos(incrementoPintura, incrementoRecurso);
                    }
                    System.out.println("No tiene suficiente nivel de Pintura para realizar un cuadro clásico.");
                    break;
                case SURREALISTA:
                    if (pintura.getNivel() > 5) {
                        modificarRecursos(incrementoPintura, incrementoRecurso);
                    }
                    System.out.println("No tiene suficiente nivel de Pintura para realizar un cuadro surrealista.");
                    break;
                case IMPRESIONISTA:
                    if (pintura.getNivel() > 5) {
                        modificarRecursos(incrementoPintura, incrementoRecurso);
                    }
                    System.out.println("No tiene suficiente nivel de Pintura para realizar un cuadro impresionista.");
                    break;
            }

            respuesta.setContent(content);
            sendMessage(respuesta);
        }
    }

    private void modificarRecursos(int incrementoPintura, int incrementoRecurso) {
        diversion.setGrado(diversion.getGrado() + incrementoRecurso);
        content.setDiversion(diversion);

        energia.setGrado(energia.getGrado() - incrementoRecurso);
        content.setEnergia(energia);

        pintura.setExperiencia(pintura.getExperiencia() + incrementoPintura);
    }
}
