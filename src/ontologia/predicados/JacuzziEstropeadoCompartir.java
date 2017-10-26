
package ontologia.predicados;

import ontologia.Predicado; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

import jadex.runtime.*;

public class JacuzziEstropeadoCompartir extends Predicado {

    private Energia energia;

    private InteraccionSocial interaccion;

    private Higiene higiene;

    

    public JacuzziEstropeadoCompartir() {

    }

    

    public Energia getEnergia() {

        return energia;

    }



    public void setEnergia(Energia energia) {

        this.energia = energia;

    }



    public InteraccionSocial getInteraccionSocial() {

           return interaccion;

       }

    public void setInteraccionSocial(InteraccionSocial interaccion) {

           this.interaccion=interaccion;

       }

  

   public Higiene getHigiene() {

        return higiene;

    }





    public void setHigiene(Higiene higiene) {

        this.higiene = higiene;



    }

}
