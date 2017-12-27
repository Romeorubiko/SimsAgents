package sala_recreativa.ajedrez;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;

public class JugarAjedrezPregunta extends Plan {

	/**
	 *  Plan body.
	 */
	public void body()
	{
		/* Creencias */
		IMessageEvent	request	= (IMessageEvent)getInitialEvent();
		RBeliefbase bb;
		bb=(RBeliefbase) getBeliefbase();
		RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_ajedrez");
		Boolean ocupado= (Boolean)creenciaOcupado.getFact();
		

		if(ocupado){
			IMessageEvent refuse = createMessageEvent("ajedrez_ocupado");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(refuse);
		}else{
			RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_jugar_ajedrez");
			RBelief creenciaTiempoFinAjedrez=(RBelief) bb.getBelief("tiempo_fin_ajedrez");
			RBelief creenciaTiempo=(RBelief) bb.getBelief("tiempo_ajedrez");
			Integer tiempo= (Integer)creenciaTiempo.getFact();

			creenciaMensaje.setFact(request);
			creenciaTiempoFinAjedrez.setFact(tiempo + Accion.TIEMPO_CORTO);
			creenciaOcupado.setFact(true);

			IMessageEvent agree = createMessageEvent("ajedrez_no_ocupado");
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(agree);

			IGoal goal= createGoal("jugar_ajedrez_tiempo_superado");
			dispatchSubgoal(goal);
		}
	}
}