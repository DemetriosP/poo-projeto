package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DisciplinaModel;

public class DisciplinaDAO {
	
	public static void insereDisciplina(DisciplinaModel disciplina) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into disciplina (disciplina_id) values (?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, disciplina.getNome());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<DisciplinaModel> selecionarDisciplinas() {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		ArrayList<DisciplinaModel> disciplinas = new ArrayList<DisciplinaModel>();
		
		String disciplina;
		
		try {
			
			String query = "select * from disciplina";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				disciplina = resultado.getString("disciplina_id");
				
				disciplinas.add(new DisciplinaModel(disciplina));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return disciplinas;
		
	}
	
	

}
