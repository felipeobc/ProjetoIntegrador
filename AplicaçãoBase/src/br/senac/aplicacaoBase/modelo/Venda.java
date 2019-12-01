
package br.senac.aplicacaoBase.modelo;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author felipe.o.camargo
 */
public class Venda {
    
     //Atributos
    private Integer id;
    private Cliente cliente;
    private List<ItemVenda> listaItemVenda
            = new ArrayList<ItemVenda>();

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ItemVenda> getListaItemVenda() {
        return listaItemVenda;
    }

    public void setListaItemVenda(List<ItemVenda> listaItemVenda) {
        this.listaItemVenda = listaItemVenda;
    }
    
    
    
}


