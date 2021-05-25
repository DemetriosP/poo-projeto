package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ifnet.Usuario;

public class UsuarioDAO {

	public static void insere(Usuario usuario) {		
		
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

	public String consulta() {
	
		Conexao conexao = new Conexao();
		
		PreparedStatement statement;
		
		ResultSet resultado = null;
		
		String prontuario = null;
		
		try {
		
			statement = conexao.getConexao().prepareStatement("select * from usuario");
			resultado = statement.executeQuery();
			
			prontuario = resultado.getString("usuario_id");
			
			statement.close();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return prontuario;
	
	}
	
}
