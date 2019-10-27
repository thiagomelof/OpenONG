package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class DoacaoItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Integer linha;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doacao")
    private Doacao doacao;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item")
    private Item item;
    @Column
    private double valorUnitario;
    @Column
    private double quantidade;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date vencimento;
    @Lob
    private String observacoes;

    private static final long serialVersionUID = 1L;

    public DoacaoItem() {
    }

    public DoacaoItem(Integer linha, Doacao doacao, Item item, double valorUnitario, double quantidade, Date vencimento, String observacoes) {
        this.linha = linha;
        this.doacao = doacao;
        this.item = item;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
        this.vencimento = vencimento;
        this.observacoes = observacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLinha() {
        return linha;
    }

    public void setLinha(Integer linha) {
        this.linha = linha;
    }

    public Doacao getDoacao() {
        return doacao;
    }

    public void setDoacao(Doacao doacao) {
        this.doacao = doacao;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

}
