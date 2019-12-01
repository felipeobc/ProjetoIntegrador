/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.aplicacaoBase.telas;

import br.senac.aplicacaoBase.mock.MockProduto;
import br.senac.aplicacaoBase.mock.MockCliente;
import br.senac.aplicacaoBase.modelo.Cliente;
import br.senac.aplicacaoBase.modelo.Produto;
import br.senac.aplicacaoBase.mock.MockVenda;
import br.senac.aplicacaoBase.modelo.ItemVenda;
import br.senac.aplicacaoBase.modelo.ModeloVenda;
import br.senac.aplicacaoBase.modelo.Venda; 
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author felipe.o.camargo
 */
public class TelaVendasController {

    @FXML
    private ComboBox<String> comboVendas;
    @FXML
    private Text qtdeEstoque;
    @FXML
    private Spinner<Integer> qtdeProdutos;
    @FXML
    private Text qtdeValor;
    @FXML
    private TableColumn<Venda, String> colunaProduto;
    @FXML
    private TableColumn<Venda, String> colunaQtdeProduto;
    @FXML
    private TableColumn<Venda, String> colunaValorTotal;
    @FXML
    private TableColumn<Venda, String> colunaCliente;
    @FXML
    private TextField txtPesquisa;
    @FXML
    private TableView<ModeloVenda> tabelaVendas;
    @FXML
    private Text txtCliente;
    @FXML
    private Button btnSalvar;
    
    private boolean editMode = false;
    private ModeloVenda itemEdicao;
    private Venda venda = new Venda();
    /**
     * Initializes the controller class.
     * @param rb
     */
    private List<ModeloVenda> listaItems;
    public void initialize() {
        
        SpinnerValueFactory<Integer> Quantidade = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999);
        qtdeProdutos.setValueFactory(Quantidade);
        
        listaItems = new ArrayList<>();
        
        colunaProduto.setCellValueFactory(
                new PropertyValueFactory("produto")
            );        
        colunaQtdeProduto.setCellValueFactory(
                new PropertyValueFactory("quantidade")
            );
        colunaValorTotal.setCellValueFactory(
                new PropertyValueFactory("valorTotal")
            );        
        colunaCliente.setCellValueFactory(
                new PropertyValueFactory("nomeCliente")
            );
        
        try{
            List<Produto> listaProduto = MockProduto.listar();
            for(int i = 0; i < listaProduto.size(); i++){
                Produto produto = listaProduto.get(i);
                comboVendas.getItems().add(produto.getEspecie());
            }
                
            
        }catch(Exception e){
            
        }
    }    

    @FXML
    private void addCarrinho(ActionEvent event) {
        int estoque = Integer.parseInt(qtdeEstoque.getText());
        int valor = Integer.parseInt(qtdeValor.getText());
        if(estoque<qtdeProdutos.getValue()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("A quantidade do produto requisitada é maior que a em estoque.");
            alert.showAndWait();
        }
        else{
            ModeloVenda itemvenda = new ModeloVenda();
            itemvenda.setProduto(comboVendas.getSelectionModel().getSelectedItem());
            itemvenda.setQuantidade(qtdeProdutos.getValue());
            itemvenda.setValorTotal(valor*qtdeProdutos.getValue());
            itemvenda.setNomeCliente(txtCliente.getText());
            
            listaItems.add(itemvenda);
            
            tabelaVendas.setItems(
                FXCollections.observableArrayList(listaItems));  
            
            
            ItemVenda item = new ItemVenda();
            try{
                Produto p = MockProduto.obterPorNome(comboVendas.getSelectionModel().getSelectedItem());
                item.setProduto(p);
                item.setQuantidade(qtdeProdutos.getValue());
                venda.getListaItemVenda().add(item);
                limparCampos();
                
            }catch (Exception ex) {
                ex.printStackTrace();
            }
            
        } 
    }
    

    @FXML
    private void finalizarCompra(ActionEvent event) {
        try {
            MockVenda.inserir(venda);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    @FXML
    private void excluirItem(ActionEvent event) {
        ModeloVenda venda = tabelaVendas.getSelectionModel().getSelectedItem();
        
        //Se há um item selecionado, habilita a edição e grava os respectivos
        //dados na variável clienteEdicao para uso posterior
        if (venda != null) {
            //Monta e exibe um diálogo de confirmação de exclusão
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Excluir item");
            alert.setContentText("Excluir o item " + venda.getProduto());
            
            //Mostra o diálogo esperando um resultado
            Optional<ButtonType> result = alert.showAndWait();
            
            //Se o resultado for afirmativo, exclui o cliente
            if (result.get() == ButtonType.OK){
                //Exclui o cliente e atualiza a tabela
                try {
                    apagarItem(venda);
                    acaoPesquisar(event);
                    updateTabela();
                }
                catch(Exception e) {
                    e.printStackTrace();
                    Alert alertErro = new Alert(AlertType.ERROR);
                    alertErro.setTitle("Erro");
                    alertErro.setContentText("Ocorreu um erro ao excluir"
                            + " o item.");
                    alertErro.showAndWait();
                }
            }
        }
        else {
            //Não há cliente selecionado, exibe uma mensagem de erro
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("É necessário selecionar um item.");
            alert.showAndWait();
        }
    }

    @FXML
    private void editarItem(ActionEvent event) {
        //Obtém o item selecionado da tabela
        ModeloVenda venda = tabelaVendas.getSelectionModel().getSelectedItem();
        
        //Se há um item selecionado, habilita a edição e grava os respectivos
        //dados na variável clienteEdicao para uso posterior
        if (venda != null) {
            
            editMode = true;
            
            itemEdicao = venda;
            
            //Coloca os dados do cliente selecionado na interface
            txtCliente.setText(itemEdicao.getNomeCliente());
            comboVendas.setValue(itemEdicao.getProduto());
            
            //Solicita o foco no campo de CPF
            comboVendas.requestFocus();
            //Atualiza o título do botão
            btnSalvar.setText("Salvar");
        }
        else {
            //Não há cliente selecionado, exibe uma mensagem de erro
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setContentText("É necessário selecionar um item.");
            alert.showAndWait();
        }
    }

    @FXML
    private void acaoPesquisar(ActionEvent event) {
         
        //Obtém os resultados de pesquisa do mock
        
        Cliente cliente = MockCliente.obterPorCpf(txtPesquisa.getText());
        txtCliente.setText(cliente.getNome());
        venda.setCliente(cliente);
       
        
    }
    
    
    private List listarPesquisar() {
        //Obtém os itens do mock
        List resultados;
  
        try {
            //Se há dados para pesquisa, faz uma busca pelo valor no mock
            //Caso contrário, faz a listagem
            if (txtPesquisa.getText().equals("")) {
                resultados = null;
            }
            else {
                resultados = MockCliente.procurar(txtPesquisa.getText());
                
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            resultados = null;
        }
        return resultados;
    }

    @FXML
    private void acaoMudaValores(ActionEvent event) {
        if(comboVendas != null) {
            
            
            try {
                Produto p = MockProduto.obterPorNome(comboVendas.getSelectionModel().getSelectedItem());
                qtdeEstoque.setText(p.getEstoque());
                qtdeValor.setText(p.getValor());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            
        }
    }
    
    private void apagarItem(ModeloVenda venda) throws Exception {
        MockVenda.excluir(venda.getId());
    }
    
    private void updateTabela(){
        List<ModeloVenda> list = listaItems;
        ObservableList<ModeloVenda> items = FXCollections.observableArrayList(list);
        tabelaVendas.setItems(items);
    }
    
    private void limparCampos(){
        txtPesquisa.setText(null);
        txtCliente.setText(null);
        comboVendas.setValue(null);
        qtdeEstoque.setText(null);
        qtdeValor.setText(null);
        
    }
    
}
