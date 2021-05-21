package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import ifnet.Professor;

public class ProfessorDAO {
	
	public void insere(Professor professor) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into professor (usuario_id, area_id, disciplina_id) values (?,?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			UsuarioDAO user = new UsuarioDAO();
			
			user.insere(professor);
			
			statement.setString(1, professor.getProntuario());
			statement.setString(2, professor.getArea().getNome());
			statement.setString(3, professor.getDisciplMinistrada().getNome());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

}
