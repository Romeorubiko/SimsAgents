
/**
 * Lizaveta Mishkinitse		NIA: 100317944
 * Raul Escabia				NIA: 100315903
 */

package casa.suelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.IGoal;
import jadex.runtime.IMessageEvent;
import jadex.runtime.Plan;
import jadex.runtime.impl.RMessageEvent;
import ontologia.Accion;

public class DormirSueloPlan extends Plan {

	@SuppressWarnings("unchecked")
	@Override
	public void body() {
		
		IMessageEvent peticion = ((IMessageEvent) getInitialEvent());
		
		ArrayList<IMessageEvent> arrayMensajes = (ArrayList<IMessageEvent>) getBeliefbase().getBelief("mensajes_dormir_suelo").getFact();
		arrayMensajes.add(peticion);
		
		getBeliefbase().getBelief("mensajes_dormir_suelo").setFact(arrayMensajes);
		
		ArrayList<Integer> arrayTiempos = (ArrayList<Integer>) getBeliefbase().getBelief("tiempos_fin_dormir_suelo").getFact();
		arrayTiempos.add((int) (System.currentTimeMillis()/1000 + Accion.TIEMPO_LARGO));
		getBeliefbase().getBelief("tiempos_fin_dormir_suelo").setFact(arrayTiempos);
		getBeliefbase().getBelief("tiempo_fin_dormir_suelo").setFact(new Integer (arrayTiempos.get(0)));


	}
}
