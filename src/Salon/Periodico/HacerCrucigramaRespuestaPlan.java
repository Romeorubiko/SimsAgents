package Salon.Periodico;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.acciones.HacerCrucigrama;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Logica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasHechoCrucigrama;

public class HacerCrucigramaRespuestaPlan extends Plan {

    public HacerCrucigramaRespuestaPlan() {

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
        getGoalbase().getGoal("crucigrama_tiempo_superado").drop();


        HacerCrucigrama content = (HacerCrucigrama)request.getContent();
        Diversion diversion= content.getDiversion();
        Energia energia= content.getEnergia();
        Logica logica= content.getLogica();

	        /*
	         * Diversión
	         */
            diversion.setGrado(content.getDiversion().getGrado()+ Necesidad.NC_NORMAL);
            content.setDiversion(diversion);

			/*
	         * Energía
	         */
            energia.setGrado(content.getEnergia().getGrado()- Necesidad.NC_POCO);
            content.setEnergia(energia);

			/*
	         * Lógica
	         */
            logica.setExperiencia(content.getLogica().getExperiencia()+ Habilidad.HB_NORMAL);
            content.setLogica(logica);


            IMessageEvent inform = createMessageEvent("has_hecho_crucigrama");
            HasHechoCrucigrama hasHechoCrucigrama= new HasHechoCrucigrama(energia,diversion,logica);
            inform.setContent(hasHechoCrucigrama);
            inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(inform);
            creenciaOcupado.setFact(false);

    }
}
