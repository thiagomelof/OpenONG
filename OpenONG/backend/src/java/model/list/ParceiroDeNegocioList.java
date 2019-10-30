package model.list;

import java.util.List;
import model.ParceiroDeNegocio;

public class ParceiroDeNegocioList {

    private List<ParceiroDeNegocio> parceiros;

    public ParceiroDeNegocioList() {
    }

    public ParceiroDeNegocioList(List<ParceiroDeNegocio> parceiros) {
        this.parceiros = parceiros;
    }

    public List<ParceiroDeNegocio> getParceiros() {
        return parceiros;
    }

    public void setParceiros(List<ParceiroDeNegocio> parceiros) {
        this.parceiros = parceiros;
    }
}
