package com.mycompany.apiprodutos.factory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexao {
    private String url = "jdbc:mysql://localhost:3306/prova";
    private String user = "root";
    private String password = "";
    
    protected Connection conexao = null;
    public Conexao() {
	    try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conexao = DriverManager.getConnection(url, user, password);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e ) {
			e.printStackTrace();
		}
    }

    public void closeConnection() {
          try {
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
}
