package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.AlunoModel;
import model.UsuarioModel;

public class AlunoDAO {
	
	public static void inserirAluno(AlunoModel aluno) {		
			
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
	
	public static boolean eAluno(String usuarioID) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		try {
			
			String query = "select * from aluno where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuarioID);
		
			resultado = statement.executeQuery();

			return resultado != null && resultado.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public static ArrayList<UsuarioModel> selecionarAlunos(){

		Conexao conexao = new Conexao();
		ResultSet resultado;

		ArrayList<UsuarioModel> alunos = new ArrayList<>();

		String usuarioID, email, cursoID;
		UsuarioModel usuario;

		try {

			String query = "select * from aluno";

			PreparedStatement statement = conexao.getConexao().prepareStatement(query);

			resultado = statement.executeQuery();

			while(resultado != null && resultado.next()){
				usuarioID = resultado.getString("usuario_id");
				email = resultado.getString("email");
				cursoID = resultado.getString("curso_id");

				usuario = UsuarioDAO.selecionaUsuario(usuarioID);

				alunos.add(new AlunoModel(usuario.getNome(), usuario.getProntuario(), usuario.getSenha(),
						email, CursoDAO.selecionarCurso(cursoID)));
			}

			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alunos;

	}
	
	public static AlunoModel selecionarAluno(String usuarioID){
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		AlunoModel aluno = null;
		
		String email, cursoID;
		UsuarioModel usuario;
		
		try {
			
			String query = "select email, curso_id from aluno where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuarioID);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				email = resultado.getString("email");
				cursoID = resultado.getString("curso_id");
				
				usuario = UsuarioDAO.selecionaUsuario(usuarioID);
				
				aluno = new AlunoModel(usuario.getNome(), usuario.getProntuario(), usuario.getSenha(),  
						email, CursoDAO.selecionarCurso(cursoID));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return aluno;
	}
	
	public static ArrayList<UsuarioModel> pesquisarAlunos(String nome){
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		ArrayList<UsuarioModel> alunos = new ArrayList<>();
		
		String email, cursoID;
		UsuarioModel usuario;

		usuario = UsuarioDAO.pesquisarUsuario(nome);
		
		try {
			
			String query = "select * from aluno where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);

			statement.setString(1, usuario.getProntuario());

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				email = resultado.getString("email");
				cursoID = resultado.getString("curso_id");

				alunos.add(new AlunoModel(usuario.getNome(), usuario.getProntuario(), usuario.getSenha(),  
						email, CursoDAO.selecionarCurso(cursoID)));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alunos;
		
	}
	
	public static void excluirAluno(UsuarioModel usuario) {
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "delete from aluno where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuario.getProntuario());
			
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		UsuarioDAO.excluirUsuario(usuario);
	}
	

}
