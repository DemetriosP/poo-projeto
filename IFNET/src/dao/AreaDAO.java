package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.AreaModel;

public class AreaDAO {
	
	public static void inserirArea(AreaModel area) {		
			
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
	
	public static ArrayList<AreaModel> selecionarAreas() {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		ArrayList<AreaModel> areas = new ArrayList<>();
		
		String area;
		
		try {
			
			String query = "select * from area_atuacao";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				area = resultado.getString("area_id");
	
				areas.add(new AreaModel(area));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return areas;
		
	}
	
}
