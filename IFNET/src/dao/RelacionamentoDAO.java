package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import ifnet.Usuario;

public class RelacionamentoDAO {
	
	public void insere(Usuario usuarios) {		
		
		Conexao conexao = new Conexao();
		
		for (Map.Entry<Integer , ArrayList<Usuario>> mapa : usuarios.getRelacionamento().getGrauUsuario().entrySet()) {
		
			for(Usuario usuario:mapa.getValue()) {
				
				try {
					
					String query = "insert into relacionamento (grau_relacionamento, usuario_relacionamento, "
							+ "usuario_relacionado) values (?,?,?)";
					
					PreparedStatement statement = conexao.getConexao().prepareStatement(query);
					
					statement.setInt(1, mapa.getKey());
					statement.setString(2, usuarios.getProntuario());
					statement.setString(3, usuario.getProntuario());
					statement.execute();
					statement.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}

}
