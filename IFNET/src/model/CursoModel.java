package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CursoModel {
	
	private String nome;
	private int semestres;
	private Map<Integer, ArrayList<DisciplinaModel>> disciplinasPorSemestre = new HashMap<>();

	public CursoModel(String nome) {
		this.nome = nome;
	}
	
	public CursoModel(String nome, int semestres) {
		this.nome = nome;
		this.semestres = semestres;
		criarMapa(semestres);
	} 
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public int getSemestres() {
		return semestres;
	}

	public void setSemestres(int semestres) {
		this.semestres = semestres;
	}
	
	public Map<Integer, ArrayList<DisciplinaModel>> getDisciplinasPorSemestre() {
		return disciplinasPorSemestre;
	}

	public void setDisciplinasPorSemestre(int semestre, DisciplinaModel disciplina) {
		this.disciplinasPorSemestre.get(semestre).add(disciplina);
	}
	
	public void setDisciplinasPorSemestre(Map<Integer, ArrayList<DisciplinaModel>> mapa) {
		this.disciplinasPorSemestre = mapa;
	}
	
	public void criarMapa(int semestres) {
		for(int semestre = 1; semestre <= semestres; semestre++) {
			this.disciplinasPorSemestre.put(semestre, new ArrayList<>());
		}
	}

	public static ArrayList<CursoModel> pesquisaCurso(ArrayList<CursoModel> cursos, String nome) {
		
		ArrayList<CursoModel> cursosPesquisados = new ArrayList<>();
		
		for(CursoModel curso:cursos) {
			if(curso.getNome().toLowerCase().contains(nome.toLowerCase())) 
				cursosPesquisados.add(curso);
		}
		
		return cursosPesquisados;
	}

	@Override
	public String toString() {
		return "Nome: " + this.nome + 
				"Quantidade de Semestres: " + this.semestres;
	}
	
	

}