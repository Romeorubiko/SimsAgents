package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.necesidades.*;
import ontologia.conceptos.*;

public class PedirBailar extends Accion {

    private Musica que;
    private Higiene higiene;

    public PedirBailar() {
    }

    public Musica getMusica() {
        return que;
    }

    public void setMusica(Musica m) {
        que = m;
    }

    public Higiene getHigiene() {
        return higiene;
    }

    public void setHigiene(Higiene h) {
        higiene = h;
    }
}
