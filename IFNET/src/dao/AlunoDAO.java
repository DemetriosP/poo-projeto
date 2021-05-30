package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ifnet.Aluno;
import ifnet.Usuario;

public class AlunoDAO {
	
	public void inserirAluno(Aluno aluno) {		
			
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into aluno (usuario_id, email, curso_id) values (?,?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			UsuarioDAO.insereUsuario(aluno);
			
			statement.setString(1, aluno.getProntuario());
			statement.setString(2, aluno.getEmail());
			statement.setString(3, aluno.getCurso().getNome());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean eAluno(String prontuario) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		try {
			
			String query = "select * from aluno where usuario_id like ?";
			
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
	
	public ArrayList<Usuario> selecionarAlunos(){
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		ArrayList<Usuario> alunos = new ArrayList<Usuario>();
		
		String usuarioID, email, cursoID;
		String[] usuario;
		
		try {
			
			String query = "select * from aluno";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				usuarioID = resultado.getString("usuario_id");
				email = resultado.getString("email");
				cursoID = resultado.getString("curso_id");
				
				usuario = UsuarioDAO.selecionaUsuario(usuarioID);
				
				alunos.add(new Aluno(usuario[0], usuarioID, email,  usuario[1], CursoDAO.selecionarCurso(cursoID)));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alunos;
		
	}
	
	public static Aluno selecionarAluno(String usuarioID){
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		Aluno aluno = null;
		
		String email, cursoID;
		String[] usuario;
		
		try {
			
			String query = "select email, curso_id from aluno where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuarioID);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				email = resultado.getString("email");
				cursoID = resultado.getString("curso_id");
				
				usuario = UsuarioDAO.selecionaUsuario(usuarioID);
				
				aluno = new Aluno(usuario[0], usuarioID, email,  usuario[1], CursoDAO.selecionarCurso(cursoID));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return aluno;
	}

}
