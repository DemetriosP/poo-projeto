package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ifnet.Area;

public class AreaDAO {
	
	public void inserirArea(Area area) {		
			
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
	
	public ArrayList<Area> selecionarArea() {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		ArrayList<Area> areas = new ArrayList<Area>();
		
		String area;
		
		try {
			
			String query = "select * from area_atuacao";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				area = resultado.getString("area_id");
				
				areas.add(new Area(area));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return areas;
		
	}
	
}
