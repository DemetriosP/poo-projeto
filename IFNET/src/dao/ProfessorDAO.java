package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ifnet.Professor;

public class ProfessorDAO {
	
	public void insere(Professor professor) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into professor (usuario_id, area_id, disciplina_id) values (?,?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			UsuarioDAO.insereUsuario(professor);
			
			statement.setString(1, professor.getProntuario());
			statement.setString(2, professor.getArea().getNome());
			statement.setString(3, professor.getDisciplMinistrada().getNome());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean eProfessor(String prontuario) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		try {
			
			String query = "select * from professor where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, prontuario);
		
			resultado = statement.executeQuery();
			
			if(resultado != null && resultado.next())return true;
			else return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

}
