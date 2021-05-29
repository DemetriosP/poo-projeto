package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ifnet.Disciplina;

public class DisciplinaDAO {
	
	public void insereDisciplina(Disciplina disciplina) {		
		
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
	
	public ArrayList<Disciplina> selecionaDisciplina() {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
		
		String disciplina;
		
		try {
			
			String query = "select * from disciplina";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				disciplina = resultado.getString("disciplina_id");
				
				disciplinas.add(new Disciplina(disciplina));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return disciplinas;
		
	}
	
	

}
