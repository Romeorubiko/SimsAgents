package ontologia.acciones;

import ontologia.Accion;
import ontologia.conceptos.habilidades.*;
import ontologia.conceptos.necesidades.*;
import ontologia.conceptos.*;

public class SacarFoto extends Accion {
    private Foto foto;
    private Energia energia;
    private Diversion diversion;
    private Fotografia fotografia;

    public SacarFoto() {
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public Energia getEnergia() {
        return energia;
    }

    public void setEnergia(Energia energia) {
        this.energia = energia;
    }

    public Diversion getDiversion() {
        return diversion;
    }

    public void setDiversion(Diversion diversion) {
        this.diversion = diversion;
    }

    public Fotografia getFotografia() {
        return fotografia;
    }

    public void setFotografia(Fotografia fotografia) {
        this.fotografia = fotografia;
    }
}
