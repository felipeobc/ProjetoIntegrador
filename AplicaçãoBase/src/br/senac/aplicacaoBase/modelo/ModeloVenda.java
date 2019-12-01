
package br.senac.aplicacaoBase.modelo;

public class ModeloVenda {
    private Integer id;
    private String produto;
    private Integer quantidade;
    private Integer valorTotal;
    private String nomeCliente;

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Integer valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }
    
}
