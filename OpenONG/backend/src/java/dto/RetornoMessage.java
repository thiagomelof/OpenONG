package dto;

import java.util.ArrayList;
import java.util.List;
import model.Erro;
import model.Resultado;

public class RetornoMessage {

    private Resultado resultado;
    private List<Erro> erros;

    public RetornoMessage() {
        resultado = new Resultado();
        erros = new ArrayList<Erro>();
    }

    public RetornoMessage(Resultado resultado, List<Erro> erros) {
        this.resultado = resultado;
        this.erros = erros;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public List<Erro> getErros() {
        return erros;
    }

    public void setErros(List<Erro> erros) {
        this.erros = erros;
    }
}
