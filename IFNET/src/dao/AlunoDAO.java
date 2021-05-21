package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import ifnet.Aluno;

public class AlunoDAO {
	
	public void insere(Aluno aluno) {		
			
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into aluno (usuario_id, email, curso_id) values (?,?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			UsuarioDAO user = new UsuarioDAO();
			
			user.insere(aluno);
			
			statement.setString(1, aluno.getProntuario());
			statement.setString(2, aluno.getEmail());
			statement.setString(3, aluno.getCurso().getNome());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

}
