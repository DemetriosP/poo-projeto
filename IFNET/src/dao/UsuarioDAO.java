package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ifnet.Usuario;

public class UsuarioDAO {

	public static void insereUsuario(Usuario usuario) {		
		
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
	
	public static String[] selecionaUsuario(String usuarioID) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		String[] usuario = new String[2];
		
		try {
			
			String query = "select * from usuario where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuarioID);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				usuario[0] = resultado.getString("nome");
				usuario[1] = resultado.getString("senha");
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
