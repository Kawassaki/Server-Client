/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfinalservidor;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alunos
 */
public class TrataCliente implements Runnable {

    private InputStream cliente;
    private Servidor servidor;
    private String comando;
    String url = "jdbc:mysql://localhost:3306/"; // endereço banco de dados padrão
    String dbName = "bancofelipe";
    String driver = "com.mysql.jdbc.Driver";
    String userName = "root";
    String password = "uni630402";

    public TrataCliente(InputStream cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void run() {
        Scanner s = new Scanner(this.cliente);
        while (s.hasNextLine()) {
            String dados = s.nextLine();
            try {
                if (dados.equals("Show Tables")  || dados.equals("SHOW TABLES")  || dados.equals("show tables")|| 
                    dados.equals("Show Tables;") || dados.equals("SHOW TABLES;") || dados.equals("show tables;")){
                    ShowTables(dados);
                } else {
                    ExecuteQuery(dados);// Método que executa o sql no Mysql
                }
            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                servidor.distribuiMensagem("Ocorreu um erro: " + ex.getMessage());
                s.close();
            }
        }
    }

    public void ExecuteQuery(String comando) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Connection conn;
        PreparedStatement stmt;
        ResultSet rs;
        ResultSetMetaData rsmd;

        QueryBrowserObjectReturn retorno = new QueryBrowserObjectReturn();
        List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
        List<ColumnQueryBrowser> columns = new ArrayList<ColumnQueryBrowser>();

        try {
            Class.forName(driver).newInstance(); //Cria conexão com o Banco
            conn = (Connection) DriverManager.getConnection(url + dbName, userName, password);//Validação da Conexão
            stmt = (PreparedStatement) conn.prepareStatement(comando); // Pega o comando do cliente e insere na variavel Statement para executar no banco
            if (comando.startsWith("Select") || comando.startsWith("SELECT") || comando.startsWith("select")) { // Verifica se o comando começa com Select. se sim
                rs = stmt.executeQuery(); //Executa o comando
                rsmd = rs.getMetaData();// Pega os dados do Select   
                int numColumns = rsmd.getColumnCount(); // Isere o numero de colunas numa variavel int

                for (int i = 1; i <= numColumns; i++) { // Loop onde varre todas as linhas que o Select retornou, e insere os dados em um ArrayList
                    String columnName = rsmd.getColumnName(i);
                    columns.add(new ColumnQueryBrowser(columnName, columnName));
                    while (rs.next()) {
                        Map<String, Object> result = new HashMap<String, Object>();
                        for (int y = 1; y <= numColumns; y++) {
                            String columnNames = rsmd.getColumnName(y);
                            Object value = rs.getObject(y);
                            result.put(columnNames, value);
                        }
                        listResult.add(result);
                    }
                    retorno.setColumns(columns);
                    retorno.setListResult(listResult);
                }
                for (Map dados1 : listResult) {//Varre o Array e envia todos os dados dele para o cliente.
                    servidor.distribuiMensagem(dados1.toString());
                }
            } else { // E se o comando não for "select" ele apenas executa no mySql e retorna a msg "Comando Executado com Sucesso!"
                stmt.execute();
                System.out.println("-------------------------------------------------------------------------------------------");
                servidor.distribuiMensagem("Comando executado com sucesso!");
            }
        } catch (SQLException e) { // Aqui mostra os erros de syntax que o cliente eventualmente cometa
            servidor.distribuiMensagem("Ocorreu um erro: " + e.getMessage());
        }
    }

    public void ShowTables(String showcomando) throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            DatabaseMetaData md = conn.getMetaData();
            String[] type = {"TABLE"};
            ResultSet rs = md.getTables(null, dbName, "%", type);
            while (rs.next()) {
                if (!rs.getString("TABLE_NAME").isEmpty()) {
                    servidor.distribuiMensagem("As tabelas criadas em seu banco de dados é: " + rs.getString("TABLE_NAME"));
                }
            }
        } catch (SQLException e) {
            

        }
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

}
