package sala_recreativa.mesadepingpong;
import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RBeliefbase;
import ontologia.Accion;

public class JugarPingpongPregunta extends Plan {

	/**
	 *  Plan body.
	 */
	public void body()
	{
		/* Creencias */
		IMessageEvent	request	= (IMessageEvent)getInitialEvent();
		RBeliefbase bb;
		bb=(RBeliefbase) getBeliefbase();
		RBelief creenciaOcupado=(RBelief) bb.getBelief("ocupado_pingpong");
		Boolean ocupado= (Boolean)creenciaOcupado.getFact();
		

		if(ocupado){
			IMessageEvent refuse = createMessageEvent("pingpong_ocupado");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(refuse);
		}else{
			RBelief creenciaMensaje=(RBelief) bb.getBelief("mensaje_jugar_pingpong");
			RBelief creenciaTiempoFinPingpong=(RBelief) bb.getBelief("tiempo_fin_pingpong");
			RBelief creenciaTiempo=(RBelief) bb.getBelief("tiempo_pingpong");
			Integer tiempo= (Integer)creenciaTiempo.getFact();

			creenciaMensaje.setFact(request);
			creenciaTiempoFinPingpong.setFact(tiempo + Accion.TIEMPO_CORTO);
			creenciaOcupado.setFact(true);

			IMessageEvent agree = createMessageEvent("pingpong_no_ocupado");
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(agree);

			IGoal goal= createGoal("jugar_pingpong_tiempo_superado");
			dispatchSubgoal(goal);
		}
	}
}