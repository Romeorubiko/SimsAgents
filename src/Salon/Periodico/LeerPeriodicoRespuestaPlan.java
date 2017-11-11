package Salon.Periodico;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.acciones.LeerPeriodico;
import ontologia.conceptos.habilidades.Escritura;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Logica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasLeidoPeriodico;

public class LeerPeriodicoRespuestaPlan extends Plan {

    public LeerPeriodicoRespuestaPlan() {

    }

    /**
     *  Plan body.
     */
    public void body()
    {
        /* Creencias */
        RBeliefbase bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_periodico");
        RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_periodico");
        IMessageEvent request= (IMessageEvent) creenciaMensaje.getFact();
        RBelief creenciaTiempoPeriodico=(RBelief) bb.getBelief("tiempo_fin_periodico");
        creenciaTiempoPeriodico.setValue(0);
        getGoalbase().getGoal("leer_periodico_tiempo_superado").drop();

        LeerPeriodico content = (LeerPeriodico)request.getContent();
        Diversion diversion= content.getDiversion();
        Energia energia= content.getEnergia();
        Logica logica= content.getLogica();
        Escritura escritura= content.getEscritura();



        /*
         * Diversión
         */
        diversion.setGrado(content.getDiversion().getGrado()+Necesidad.NC_NORMAL);
        content.setDiversion(diversion);

        /*
         * Energía
         */
        energia.setGrado(content.getEnergia().getGrado()- Necesidad.NC_POCO);
        content.setEnergia(energia);

        /*
         * Lógica
         */
        logica.setExperiencia(content.getLogica().getExperiencia()+Habilidad.HB_POCO);
        content.setLogica(logica);

        /*
         * Escritura
         */
        escritura.setExperiencia(content.getEscritura().getExperiencia()+Habilidad.HB_POCO);
        content.setEscritura(escritura);

        IMessageEvent inform = createMessageEvent("has_leido_periodico");
        HasLeidoPeriodico hasLeidoPeriodico= new HasLeidoPeriodico(energia,diversion,logica,escritura);
        inform.setContent(hasLeidoPeriodico);
        inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
        sendMessage(inform);
        creenciaOcupado.setFact(false);
    }
}

