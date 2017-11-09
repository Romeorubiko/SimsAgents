package dormitorio.caballete;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import ontologia.Accion;
import ontologia.acciones.PintarNuevoCuadro;
import ontologia.conceptos.Cuadro;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Pintura;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.CuadroPintado;
import ontologia.predicados.CuadroTerminado;

public class PintarNuevoCuadroRespuestaPlan extends Plan {

    private PintarNuevoCuadro content;
    private Diversion diversion;
    private Energia energia;
    private Pintura pintura;

    public PintarNuevoCuadroRespuestaPlan() {
    }

    @Override
    public void body() {
        IMessageEvent peticion = (IMessageEvent) getBeliefbase().getBelief("mensaje_pintar_nuevo_cuadro").getFact();
        getBeliefbase().getBelief("tiempo_fin_caballete").setFact(0);
        getBeliefbase().getBelief("ocupado_caballete").setFact(Boolean.FALSE);
        getGoalbase().getGoal("pintar_nuevo_cuadro_tiempo_superado").drop();

        content = (PintarNuevoCuadro) peticion.getContent();
        Cuadro cuadro = content.getCuadro();
        diversion = content.getDiversion();
        energia = content.getEnergia();
        pintura = content.getPintura();

        int incrementoPintura = Habilidad.HB_MUCHO;
        int incrementoRecurso = Necesidad.NC_NORMAL;

        IMessageEvent respuesta = createMessageEvent("cuadro_pintado");
        respuesta.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());

        CuadroPintado cuadroPintado = new CuadroPintado(energia, diversion, pintura);
        respuesta.setContent(cuadroPintado);

        int tiempoRestante = cuadro.getTiempoPintado() - Accion.TIEMPO_MEDIO;
        if (tiempoRestante <= 0) {
            incrementoPintura = Habilidad.HB_POCO;
            incrementoRecurso = Necesidad.NC_POCO;
            respuesta = createMessageEvent("cuadro_terminado");
            CuadroTerminado cuadroTerminado = new CuadroTerminado(energia, diversion, pintura);
            respuesta.setContent(cuadroTerminado);
            getBeliefbase().getBelief("cuadro_instalado").setFact(null);
            cuadro.setTiempoPintado(0);
        } else {
            cuadro.setTiempoPintado(tiempoRestante);
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

        sendMessage(respuesta);
    }

    private void modificarRecursos(int incrementoPintura, int incrementoRecurso) {
        diversion.setGrado(diversion.getGrado() + incrementoRecurso);
        content.setDiversion(diversion);

        energia.setGrado(energia.getGrado() - incrementoRecurso);
        content.setEnergia(energia);

        pintura.setExperiencia(pintura.getExperiencia() + incrementoPintura);
    }
}
