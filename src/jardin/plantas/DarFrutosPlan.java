package jardin.plantas;
import ontologia.Accion;
import ontologia.acciones.*;
import ontologia.predicados.*;

import java.util.*;

import jadex.runtime.*;
import jadex.runtime.impl.RMessageEvent;
import jadex.adapter.fipa.*;

public class DarFrutosPlan extends Plan{

	public void body(){
		 getBeliefbase().getBelief("hay_frutos").setFact(Boolean.TRUE);
	}
}
