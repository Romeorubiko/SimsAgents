package casa.suelo;

import java.util.ArrayList;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RBelief;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;

/**
 * Created by eldgb on 25-Oct-17.
 */
public class DormirSueloPlan extends Plan {

	@Override
	public void body() {
		RMessageEvent peticion = ((RMessageEvent) getInitialEvent());

		IMessageEvent agree = createMessageEvent("suelo_no_ocupado");
		agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
		sendMessage(agree);
		
		@SuppressWarnings("unchecked")
		ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>)getBeliefbase().getBelief("mensajes_dormir_suelo").getFact();
		arrayMensajes.add(agree);
		getBeliefbase().getBelief("mensajes_dormir_suelo").setFact(arrayMensajes);
		
		@SuppressWarnings("unchecked")
		ArrayList<Integer> arrayTiempos = (ArrayList<Integer>)getBeliefbase().getBelief("tiempos_dormir_suelo").getFact();
		arrayTiempos.add((int) (System.currentTimeMillis() + Accion.TIEMPO_LARGO));
		getBeliefbase().getBelief("tiempos_dormir_suelo").setFact(arrayTiempos);

		IGoal goal = createGoal("terminar_dormir_suelo");
		dispatchSubgoal(goal);

	}
}
