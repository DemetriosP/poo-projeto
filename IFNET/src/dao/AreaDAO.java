package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import ifnet.Area;

public class AreaDAO {
	
	public void insere(Area area) {		
			
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into area_atuacao (area_id) values (?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, area.getNome());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

}
