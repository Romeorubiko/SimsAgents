package dormitorio.caballete;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.PintarNuevoCuadro;
import ontologia.acciones.SeguirPintando;
import ontologia.conceptos.Cuadro;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Pintura;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.CuadroPintado;
import ontologia.predicados.CuadroTerminado;

public class SeguirPintandoRespuestaPlan extends Plan {

    private Diversion diversion;
    private Energia energia;
    private Pintura pintura;

    public SeguirPintandoRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje_caballete").getFact();
        getBeliefbase().getBelief("tiempo_fin_caballete").setFact(0);
        getBeliefbase().getBelief("ocupado_caballete").setFact(Boolean.FALSE);

        SeguirPintando content = (SeguirPintando) peticion.getContent();
        Cuadro cuadro = (Cuadro) getBeliefbase().getBelief("cuadro_instalado").getFact();
        diversion = content.getDiversion();
        energia = content.getEnergia();
        pintura = content.getPintura();

        int incrementoPintura = Habilidad.HB_MUCHO;
        int incrementoRecurso = Necesidad.NC_NORMAL;

        IMessageEvent respuesta = createMessageEvent("cuadro_pintado");
        respuesta.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());

        int tiempoRestante = cuadro.getTiempoPintado() - Accion.TIEMPO_MEDIO;
        if (tiempoRestante <= 0) {
            incrementoPintura = Habilidad.HB_POCO;
            incrementoRecurso = Necesidad.NC_POCO;
            respuesta = createMessageEvent("cuadro_terminado");
            getBeliefbase().getBelief("cuadro_instalado").setFact(null);
            cuadro.setTiempoPintado(0);
        } else {
            cuadro.setTiempoPintado(tiempoRestante);
        }
        getBeliefbase().getBelief("cuadro_instalado").setFact(cuadro);

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

        if (tiempoRestante <= 0) {
            CuadroTerminado cuadroTerminado = new CuadroTerminado(energia, diversion, pintura);
            respuesta.setContent(cuadroTerminado);
        } else {
            CuadroPintado cuadroPintado = new CuadroPintado(energia, diversion, pintura);
            respuesta.setContent(cuadroPintado);
        }
        sendMessage(respuesta);
    }

    private void modificarRecursos(int incrementoPintura, int incrementoRecurso) {
        diversion.setGrado(diversion.getGrado() + incrementoRecurso);
        energia.setGrado(energia.getGrado() - incrementoRecurso);
        pintura.setExperiencia(pintura.getExperiencia() + incrementoPintura);
    }
}
