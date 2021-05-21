package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ifnet.Grupo;
import ifnet.Usuario;

public class GrupoDAO {
	
	public static void insere(Grupo grupo) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into grupo (nome, disciplina_id, usuario_id, tipo) values (?,?,?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, grupo.getNome());
			statement.setString(2, grupo.getDisciplina().getNome());
			statement.setString(3, grupo.getCriador().getProntuario());
			statement.setString(4, grupo.getTipo());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void inserirUsuariosGrupo(Grupo grupo) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		int grupo_id = 0;
		
		try {
			
			String query = "select grupo_id from grupo where nome like ? and disciplina_id like ? and usuario_id like ? and tipo like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, grupo.getNome());
			statement.setString(2, grupo.getDisciplina().getNome());
			statement.setString(3, grupo.getCriador().getProntuario());
			statement.setString(4, grupo.getTipo());
			
			resultado = statement.executeQuery();
			
			if(resultado != null && resultado.next()){
				grupo_id = resultado.getInt("grupo_id");
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(Usuario usuario:grupo.getUsuariosGrupo()) {
			
			try {
				
				String query = "insert into usuarios_grupo (grupo_id, usuario_id) values (?,?)";
				
				PreparedStatement statement = conexao.getConexao().prepareStatement(query);
				
				statement.setInt(1, grupo_id);
				statement.setString(2, usuario.getProntuario());
				statement.execute();
				statement.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
