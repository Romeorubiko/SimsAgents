package sala_recreativa.guitarra;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.acciones.TocarGuitarra;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Guitarra;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasTocado;


public class TocarGuitarraRespuesta extends Plan {
	public TocarGuitarraRespuesta() {
    }

    @Override
    public void body() {
        /* Creencias */
        RBeliefbase bb;
        bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_guitarra");
        RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_tocar_guitarra");
        IMessageEvent request= (IMessageEvent) creenciaMensaje.getFact();
        RBelief creenciaTiempoPeriodico=(RBelief) bb.getBelief("tiempo_fin_guitarra");
        creenciaTiempoPeriodico.setValue(0);
        getGoalbase().getGoal("tiempo_guitarra_superado").drop();


        TocarGuitarra content = (TocarGuitarra) request.getContent();

            Energia e = content.getEnergia();
            e.setGrado(e.getGrado() - Necesidad.NC_POCO);
            content.setEnergia(e);

            Diversion d = content.getDiversion();
            d.setGrado(d.getGrado() + Necesidad.NC_NORMAL);
            content.setDiversion(d);

            Guitarra g = content.getGuitarra();
            g.setExperiencia(g.getExperiencia() + Habilidad.HB_MUCHO);
            content.setGuitarra(g);


        IMessageEvent inform = createMessageEvent("has_tocado_guitarra");
        HasTocado hasTocadoGuitarra = new HasTocado(e, d, g);
        inform.setContent(hasTocadoGuitarra);
        inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
        sendMessage(inform);
        creenciaOcupado.setFact(false);

        }
    }  