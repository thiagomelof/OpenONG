package model;

import constantes.TipoRegistro;

public class Resultado {

    private long id;
    private int tipoRegistro;

    public Resultado() {
        id = 0;
        tipoRegistro = TipoRegistro.INDEFINIDO;
    }

    public Resultado(int id, int tipoRegistro) {
        this.id = id;
        this.tipoRegistro = tipoRegistro;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(int tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }
}
