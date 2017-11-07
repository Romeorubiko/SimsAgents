package ontologia.acciones;

import ontologia.Accion; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public class JugarVideojuego extends Accion {
    private Juego tipo;
    private Energia energia;
    private Diversion diversion;
    private Logica logica;

    public JugarVideojuego() {
    }

    public Juego getTipo() {
        return tipo;
    }

    public void setTipo(Juego tipo) {
        this.tipo = tipo;
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

    public Logica getLogica() {
        return logica;
    }

    public void setLogica(Logica logica) {
        this.logica = logica;
    }
}
