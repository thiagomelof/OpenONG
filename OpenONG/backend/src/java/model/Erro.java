package model;

import constantes.CodigoErro;

public class Erro {

    private int codErro;
    private String msgErro;

    public Erro() {
        codErro = -1;
        msgErro = "";
    }

    public Erro(int codErro, String msgErro) {
        this.codErro = codErro;
        this.msgErro = msgErro;
    }

    public int getCodErro() {
        return codErro;
    }

    public void setCodErro(int codErro) {
        this.codErro = codErro;
    }

    public String getMsgErro() {
        return msgErro;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }

}
