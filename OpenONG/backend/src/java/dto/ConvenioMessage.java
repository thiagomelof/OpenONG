package dto;

import java.util.List;
import model.Convenio;
import model.ConvenioCategoria;

public class ConvenioMessage {

    private Convenio convenio;
    private List<ConvenioCategoria> categorias;

    public ConvenioMessage() {
    }

    public ConvenioMessage(Convenio convenio, List<ConvenioCategoria> categorias) {
        this.convenio = convenio;
        this.categorias = categorias;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public List<ConvenioCategoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<ConvenioCategoria> categorias) {
        this.categorias = categorias;
    }
}
