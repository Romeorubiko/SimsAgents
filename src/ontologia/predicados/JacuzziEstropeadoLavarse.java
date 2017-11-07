
package ontologia.predicados;



import ontologia.Predicado;
import ontologia.conceptos.necesidades.*;

public class JacuzziEstropeadoLavarse extends Predicado {

    private Energia energia;
    private Higiene higiene;

    

    public JacuzziEstropeadoLavarse() {

    }

   

    public Energia getEnergia() {

        return energia;

    }



    public void setEnergia(Energia energia) {

        this.energia = energia;

    }

  

   public Higiene getHigiene() {

        return higiene;

    }


    public void setHigiene(Higiene higiene) {

        this.higiene = higiene;



    }

}
