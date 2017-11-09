package Salon;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;
import ontologia.acciones.HacerCrucigrama;
import ontologia.conceptos.habilidades.Habilidad;
import ontologia.conceptos.habilidades.Logica;
import ontologia.conceptos.necesidades.Diversion;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.Necesidad;
import ontologia.predicados.HasHechoCrucigrama;

public class HacerCrucigramaRespuestaPlan extends Plan {
    /**
     *  Plan body.
     */
    public void body()
    {


		/* Creencias */
        RBeliefbase bb;
        bb=(RBeliefbase) getBeliefbase();
        RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_periodico");
        RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_periodico");
        IMessageEvent request= (IMessageEvent) creenciaMensaje.getFact();
        RBelief creenciaTiempoPeriodico=(RBelief) bb.getBelief("tiempo_fin_periodico");
        creenciaTiempoPeriodico.setValue(0);
        getGoalbase().getGoal("hacer_crucigrama_tiempo_superado").drop();


        HacerCrucigrama content = (HacerCrucigrama)request.getContent();
        Diversion diversion= content.getDiversion();
        Energia energia= content.getEnergia();
        Logica logica= content.getLogica();

	        /*
	         * Se actualiza el grado de diversi�n
	         */
            diversion.setGrado(content.getDiversion().getGrado()+ Necesidad.NC_NORMAL);
            content.setDiversion(diversion);

			/*
	         * Se actualiza el grado de energ�a
	         */
            energia.setGrado(content.getEnergia().getGrado()- Necesidad.NC_POCO);
            content.setEnergia(energia);

			/*
	         * Se actualiza la experiencia en l�gica
	         */
            logica.setExperiencia(content.getLogica().getExperiencia()+ Habilidad.HB_NORMAL);
            content.setLogica(logica);

            try {
                wait(Accion.TIEMPO_CORTO);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            IMessageEvent inform = createMessageEvent("has_hecho_crucigrama");
            HasHechoCrucigrama hasHechoCrucigrama= new HasHechoCrucigrama(energia,diversion,logica);
            inform.setContent(hasHechoCrucigrama);
            inform.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
            sendMessage(inform);
            creenciaOcupado.setFact(false);

    }
}
