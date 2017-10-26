package ontologia.predicados;



import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

import jadex.runtime.*;



public class VaterEstropeado extends Predicado {

    private Energia energia;

    private Vejiga vejiga;





    public VaterEstropeado() {

    }



    public Energia getEnergia() {

        return energia;

    }



    public void setEnergia(Energia energia) {

        this.energia = energia;

    }



   public Vejiga getVejiga() {

        return vejiga;

    }



    public void setVejiga(Vejiga vejiga) {

        this.vejiga = vejiga;

    }





}
