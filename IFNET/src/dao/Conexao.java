package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	Connection conexao = null;
	
	public Conexao() {
		try {
			conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetopoo?user=root&password=Ero_159@");
		} catch (SQLException excecao) {
			excecao.printStackTrace();
		}
	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}
	
}
