package ontologia.acciones;

import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class BailarConSim extends Accion {
	
       	private Musica que;
   	private InteraccionSocial interaccion;
	private Energia energia;
	private Diversion diversion;
	private Higiene higiene;
	private Hambre hambre;
	private Deporte fisico;
	   
   	public BailarConSim()
       	{
        }
	   
   	public Musica getMusica() {
   		return que;
       	}
	public void setMusica(Musica m) {
   		que=m;
       	}
	   
   	public InteraccionSocial getInteraccionSocial() {
   		return interaccion;
       	}
       	public void setInteraccionSocial(InteraccionSocial i) {
   		interaccion=i;
       	}
	
	    
    	public Diversion getDiversion() {
        	return diversion;
    	}
    	public void setDiversion(Diversion d) {
        	diversion=d;
    	}
	
	public Energia getEnergia() {
        	return energia;
    	}
    	public void setEnergia(Energia e) {
        	energia=e;
    	}
    
	public Hambre getHambre() {
        	return hambre;
    	}
    	public void setHambre(Hambre h) {
        	hambre=h;
    	}
    
	public Higiene getHigiene() {
        	return higiene;
    	}
    	public void setHigiene(Higiene h) {
        	higiene=h;
    	}

	public Deporte getDeporte() {
		return fisico;
	}
	public void setDeporte(Deporte f) {
		fisico=f;
	}
}
