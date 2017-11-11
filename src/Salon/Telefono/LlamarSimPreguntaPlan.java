package Salon.Telefono;
import java.util.Random;

import jadex.adapter.fipa.SFipa;
import jadex.runtime.*;
import jadex.runtime.impl.RBelief;
import ontologia.Accion;
import ontologia.acciones.LlamarSim;
import ontologia.conceptos.habilidades.Carisma;
import ontologia.conceptos.necesidades.Energia;
import ontologia.conceptos.necesidades.InteraccionSocial;
import ontologia.predicados.LlamadaFallida;

public class LlamarSimPreguntaPlan extends Plan {

	public LlamarSimPreguntaPlan() {
	}
	
	public void body()
	{
		/* Obtención del request que inicia el plan */
		IMessageEvent	request	= (IMessageEvent)getInitialEvent();
		LlamarSim content = (LlamarSim) request.getContent();
		InteraccionSocial interaccion = content.getInteraccionSocial();
		Energia energia = content.getEnergia();
		Carisma carisma = content.getCarisma();
		
		/* Obtención de las creencias del agente */
		RBelief creenciaMensaje=(RBelief) getBeliefbase().getBelief("mensaje");
		RBelief creenciaTiempoFin=(RBelief) getBeliefbase().getBelief("tiempo_fin_llamada");
		RBelief creenciaOcupado=(RBelief) getBeliefbase().getBelief("telefono_ocupado");
		Boolean ocupado= (Boolean)creenciaOcupado.getFact();

		/* Si el teléfono está ocupado se rechaza la petición, si no se acepta*/
		if(ocupado){
			/* Envío del refuse */
			IMessageEvent refuse = createMessageEvent("peticion_rechazada");
			refuse.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(refuse);
		}else{
			/* Envío del agree */
			IMessageEvent agree = createMessageEvent("peticion_aceptada");
			agree.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
			sendMessage(agree);
			
			/* Probabilidad de que la llamada falle */
			Boolean falloLlamada = new Random().nextInt(20) == 0;
			
			/* Si la llamada falla se envía un failure. Si no, pasará a ejecutarse el plan de LlamarSimRespuestaPlan */
			if(falloLlamada) {
				IMessageEvent failure = createMessageEvent("llamada_fallida");
				LlamadaFallida llamadaFallida = new LlamadaFallida(energia, interaccion, carisma);
				failure.setContent(llamadaFallida);
				failure.getParameterSet(SFipa.RECEIVERS).addValue(request.getParameterSet(SFipa.SENDER).getValues());
				sendMessage(failure);
			}
			else {
				/* Se guarda el contenido del request en una creencia para que el segundo plan pueda acceder a dicho contenido */
				creenciaMensaje.setFact(request);
				
				/* Ahora el teléfono está ocupado, se actualiza la creencia*/
				creenciaOcupado.setFact(true);
				
				/* Obtención del tiempo que lleva ejecutándose el agente */
				int tiempo=(int) getBeliefbase().getBelief("tiempo_actual").getFact();
				
				/* Se guarda en una creencia el tiempo en el que se va a terminar la llamada*/
				creenciaTiempoFin.setFact(tiempo + Accion.TIEMPO_CORTO);
				
				/* Lanzamiento del objetivo */
				IGoal goal= createGoal("llamar_sim_tiempo_superado");
				dispatchTopLevelGoal(goal);
			}
		}
	}
}