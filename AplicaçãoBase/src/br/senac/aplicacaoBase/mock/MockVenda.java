package br.senac.aplicacaoBase.mock;

import br.senac.aplicacaoBase.modelo.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import br.senac.aplicacaoBase.modelo.Venda;

//Mock de Cliente. Realiza operações de mock com o cliente. 
public class MockVenda {
    private static int totalVenda = 0;
    /** Armazena a lista de clientes inseridos para manipulação. #MOCK **/    
    private static List<Venda> listaVenda = new ArrayList<Venda>();
    
    //Insere um cliente no mock "cliente"
    public static void inserir(Venda venda)
            throws Exception {
        venda.setId(totalVenda++);
        listaVenda.add(venda);
    }
    
    public static void excluir(Integer id) throws Exception {
        if (id != null && !listaVenda.isEmpty()) {
            for (int i = 0; i < listaVenda.size(); i++) {
                Venda vendaLi = listaVenda.get(i);
                if (vendaLi != null && vendaLi.getId() == id) {
                    listaVenda.remove(i);
                    break;
                }
            }
        }
    }
    
    

    
}