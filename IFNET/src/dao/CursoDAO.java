package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import ifnet.Curso;
import ifnet.Disciplina;

public class CursoDAO {
	
	public void insere(Curso curso) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into curso (curso_id, semestres ) values (?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, curso.getNome());
			statement.setInt(2, curso.getSemestres());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		inserirDisciplinaPorSemestre(curso);
	}
	
	private void inserirDisciplinaPorSemestre(Curso curso) {
		
		Conexao conexao = new Conexao();
		
		for (Map.Entry<Integer , ArrayList<Disciplina>> mapa : curso.getDisciplinasPorSemestre().entrySet()) { 
			
			for(Disciplina disciplina: mapa.getValue()) {
				
				try {
					
					String query = "insert into disciplina_semestre (semestre, disciplina_id, curso_id) values (?,?,?)";
					
					PreparedStatement statement = conexao.getConexao().prepareStatement(query);
					
					statement.setInt(1, mapa.getKey());
					statement.setString(2, disciplina.getNome());
					statement.setString(3, curso.getNome());
					statement.execute();
					statement.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		}
			
	}
	
	

}
