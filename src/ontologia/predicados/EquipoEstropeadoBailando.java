package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

public class EquipoEstropeadoBailando extends Predicado {

	private Energia energia;
	private Diversion diversion;
	private Higiene higiene;
	private Hambre hambre;
	private Deporte fisico;

	public EquipoEstropeadoBailando() {
		;
	}

	public EquipoEstropeadoBailando(Energia energia, Diversion diversion, Higiene higiene, Hambre hambre,
			Deporte fisico) {
		this.energia = energia;
		this.diversion = diversion;
		this.higiene = higiene;
		this.hambre = hambre;
		this.fisico = fisico;
	}

	public Diversion getDiversion() {
		return diversion;
	}

	public void setDiversion(Diversion d) {
		diversion = d;
	}

	public Energia getEnergia() {
		return energia;
	}

	public void setEnergia(Energia e) {
		energia = e;
	}

	public Hambre getHambre() {
		return hambre;
	}

	public void setHambre(Hambre h) {
		hambre = h;
	}

	public Higiene getHigiene() {
		return higiene;
	}

	public void setHigiene(Higiene h) {
		higiene = h;
	}

	public Deporte getDeporte() {
		return fisico;
	}

	public void setDeporte(Deporte f) {
		fisico = f;
	}
}