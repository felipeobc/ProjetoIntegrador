package br.senac.aplicacaoBase.connectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

//Obtém um objeto de conexão do banco de dados.
//Pode ser utilizado para abertura e fechamento de conexões com o banco
public class ConnectionUtils {

    //Obtém uma conexão do banco de dados
    public static Connection getConnection() throws Exception {
    	
        //Conexão para abertura e fechamento
        Connection connection = null;
        
        //Só tenta abrir uma conexão se não existir ou estiver fechada            
        //Endereço de conexão com o banco de dados
        String dbURL = "jdbc:mysql://localhost:3306/pi";
        
        //Propriedades para armazenamento de usuário e senha
        Properties properties = new Properties();
        properties.put("user", "root");
        properties.put("password", "adminadmin");
        properties.put("serverTimezone", "UTC");
        
        //Realiza a conexão com o banco
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(dbURL, properties);

        //Retorna a conexão
        return connection;
    }
}

