package ontologia.conceptos;

public class Foto {
    public enum TiposFoto {PAISAJE, PANORAMICA}
    public enum FotoSize {SMALL, MEDIUM, LARGE}
    public enum Filtro {COLOR, BYN, SEPIA, VIGNETTE}

    private TiposFoto tipo;
    private FotoSize size;
    private Filtro filtro;

    public Foto() {
    }

    public TiposFoto getTipo() {
        return tipo;
    }

    public void setTipo(TiposFoto tipo) {
        this.tipo = tipo;
    }

    public FotoSize getSize() {
        return size;
    }

    public void setSize(FotoSize size) {
        this.size = size;
    }

    public Filtro getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }
}
