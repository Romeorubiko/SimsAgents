package casa.suelo;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;

/**
 * Created by eldgb on 02-Nov-17.
 */
public class OrinarSueloPlan extends Plan {

	@Override
	public void body() {
		RMessageEvent peticion = ((RMessageEvent) getInitialEvent());

		IMessageEvent agree = createMessageEvent("suelo_no_ocupado");
		agree.getParameterSet(SFipa.RECEIVERS).addValue(peticion.getParameterSet(SFipa.SENDER).getValues());
		sendMessage(agree);

		getBeliefbase().getBelief("mensaje_orinar_suelo").setFact(peticion);

		int end_timer = (int) System.currentTimeMillis() + Accion.TIEMPO_LARGO;
		getBeliefbase().getBelief("tiempo_fin_orinar_suelo").setFact(new Integer(end_timer));

		IGoal goal = createGoal("terminar_orinar_suelo");
		dispatchSubgoal(goal);

	}
}
