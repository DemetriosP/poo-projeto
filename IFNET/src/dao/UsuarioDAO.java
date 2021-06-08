package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.UsuarioModel;

public class UsuarioDAO {

	public static void insereUsuario(UsuarioModel usuario) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into usuario (usuario_id, nome, senha) values (?,?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuario.getProntuario());
			statement.setString(2, usuario.getNome());
			statement.setString(3, usuario.getSenha());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static UsuarioModel selecionaUsuario(String usuarioID) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		String nome = null, senha = null;
		
		try {
			
			String query = "select * from usuario where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuarioID);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				nome = resultado.getString("nome");
				senha = resultado.getString("senha");
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new UsuarioModel(nome, usuarioID, senha);
	}
	
	public static UsuarioModel pesquisarUsuario(String nome) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		String usuarioID = null, senha = null;
		
		try {
			
			String query = "select * from usuario where nome like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, nome);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				usuarioID = resultado.getString("usuario_id");
				senha = resultado.getString("senha");
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new UsuarioModel(nome, usuarioID, senha);
	}
	
	public static boolean usuarioExiste(String prontuario) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		try {
			
			String query = "select * from usuario where usuario_id like ?";
			
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
	
	public static boolean loginUsuario(UsuarioModel usuario) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		try {
			
			String query = "select * from usuario where usuario_id like ? and senha like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuario.getProntuario());
			statement.setString(2, usuario.getSenha());
		
			resultado = statement.executeQuery();
			
			if(resultado != null && resultado.next())return true;
			else return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void mudarNome(UsuarioModel usuarioAtual, String nome) {
		
		Conexao conexao = new Conexao();
		
		try {
			
			String query = "update usuario set nome = ? where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, nome);
			statement.setString(2, usuarioAtual.getProntuario());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void mudarSenha(UsuarioModel usuarioAtual, String senha) {
		
		Conexao conexao = new Conexao();
		
		try {
			
			String query = "update usuario set senha = ? where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, senha);
			statement.setString(2, usuarioAtual.getProntuario());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void excluirUsuario(UsuarioModel usuario) {
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "delete from usuario where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuario.getProntuario());
			
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	 
}
