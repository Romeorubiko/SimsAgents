package ontologia.predicados;

import ontologia.Predicado;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;

public class HasBailado extends Predicado {

	private Energia energia;
	private Higiene higiene;
	private Diversion diversion;
	private Hambre hambre;
	private Deporte fisico;

	public HasBailado() {
		;
	}

	public HasBailado(Energia energia, Higiene higiene, Diversion diversion, Hambre hambre, Deporte fisico) {
		this.energia = energia;
		this.higiene = higiene;
		this.diversion = diversion;
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
