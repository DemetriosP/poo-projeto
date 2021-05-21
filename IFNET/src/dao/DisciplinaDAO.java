package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import ifnet.Disciplina;

public class DisciplinaDAO {
	
	public void insere(Disciplina disciplina) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into disciplina (disciplina_id) values (?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, disciplina.getNome());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
