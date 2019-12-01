package br.senac.aplicacaoBase.aplicacao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Aplicacao extends Application {

    /**
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent telaCadastro = FXMLLoader.load(
                getClass().getResource(
                        "/br/senac/aplicacaoBase/telas/TelaInicial.fxml"
                )
        );
        
        Scene scene = new Scene(telaCadastro);
        
        stage.setScene(scene);
        stage.setTitle("Aplicação Base");
        stage.setMinHeight(400);
        stage.setMinWidth(500);
        
        stage.show();
    }   
    
}
