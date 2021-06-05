package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.CursoModel;
import model.DisciplinaModel;

public class CursoDAO {
	
	public static void inserirCurso(CursoModel curso) {		
		
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
	
	private static void inserirDisciplinaPorSemestre(CursoModel curso) {
		
		Conexao conexao = new Conexao();
		
		for (Map.Entry<Integer , ArrayList<DisciplinaModel>> mapa : curso.getDisciplinasPorSemestre().entrySet()) { 
			
			for(DisciplinaModel disciplina: mapa.getValue()) {
				
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
	
	public static ArrayList<CursoModel> selecionarCursos() {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		ArrayList<CursoModel> cursos = new ArrayList<CursoModel>();
		
		CursoModel curso;
		String cursoID;
		int semestres;
		
		try {
			
			String query = "select * from curso";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				cursoID = resultado.getString("curso_id");
				semestres = resultado.getInt("semestres");
				
				curso = new CursoModel(cursoID, semestres);
				curso.setDisciplinasPorSemestre(selecionarDisciplinaSemestre(cursoID));
				
				cursos.add(curso);
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return cursos;
	}
	
	public static CursoModel selecionarCurso(String cursoID) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
	
		CursoModel curso = null;
		int semestres;
		
		try {
			
			String query = "select semestres from curso where curso_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, cursoID);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				semestres = resultado.getInt("semestres");
				
				curso = new CursoModel(cursoID, semestres);
				curso.setDisciplinasPorSemestre(selecionarDisciplinaSemestre(cursoID));

			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return curso;
	}
	
	private static Map<Integer, ArrayList<DisciplinaModel>> selecionarDisciplinaSemestre(String cursoID){
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		Map<Integer, ArrayList<DisciplinaModel>> disciplinasPorSemestre = new HashMap<Integer, ArrayList<DisciplinaModel>>();
		
		String disciplina;
		int semestre;
		
		try {
			
			String query = "select disciplina_id, semestre from disciplina_semestre where curso_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, cursoID);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				disciplina = resultado.getString("disciplina_id");
				semestre = resultado.getInt("semestre");
				
				if(disciplinasPorSemestre.containsKey(semestre)) {
					disciplinasPorSemestre.get(semestre).add(new DisciplinaModel(disciplina));
				}else {
					disciplinasPorSemestre.put(semestre, new ArrayList<DisciplinaModel>());
					disciplinasPorSemestre.get(semestre).add(new DisciplinaModel(disciplina));
				}
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return disciplinasPorSemestre;
	}
	
}
