package model;

public class ProfessorModel extends UsuarioModel{
	
	private AreaModel area;
	private DisciplinaModel disciplMinistrada;
	
	public ProfessorModel(String prontuario) {
		super(prontuario);
	}
	
	public ProfessorModel(String nome, String prontuario, String senha, AreaModel area, DisciplinaModel disciplMinistrada) {
		super(nome, prontuario, senha);
		this.area = area;
		this.disciplMinistrada = disciplMinistrada;
	}
	
	public AreaModel getArea() {
		return area;
	}
	
	public void setArea(AreaModel area) {
		this.area = area;
	}

	public DisciplinaModel getDisciplMinistrada() {
		return disciplMinistrada;
	}

	public void setDisciplMinistrada(DisciplinaModel disciplMinistrada) {
		this.disciplMinistrada = disciplMinistrada;
	}
	
	public String toString() {
		return "Nome: " + getNome() + 
				"\nProntu·rio: " + getProntuario() +
				"\n¡rea: " + this.area.getNome() +
				"\nDisciplina: " + this.disciplMinistrada.getNome();
	}
	
	

}
