/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */

package casa.suelo;

import java.util.ArrayList;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;

public class OrinarSueloPlan extends Plan {

	@SuppressWarnings("unchecked")
	@Override
	public void body() {
		IMessageEvent peticion = ((IMessageEvent) getInitialEvent());


		ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) getBeliefbase()
				.getBelief("mensajes_orinar_suelo").getFact();
		arrayMensajes.add(peticion);
		getBeliefbase().getBelief("mensajes_orinar_suelo").setFact(arrayMensajes);

		ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) getBeliefbase().getBelief("tiempos_orinar_suelo")
				.getFact();
		arrayTiempos.add((int) (System.currentTimeMillis() + Accion.TIEMPO_CORTO));
		getBeliefbase().getBelief("tiempos_orinar_suelo").setFact(arrayTiempos);
		getBeliefbase().getBelief("tiempo_orinar_suelo").setFact(arrayTiempos.get(0));
		
		/**if (((ArrayList<IMessageEvent>) getBeliefbase().getBelief("mensajes_orinar_suelo").getFact()).size() == 1) {
			IGoal goal = createGoal("terminar_orinar_suelo");
			dispatchSubgoal(goal);
		}**/

	}
}
