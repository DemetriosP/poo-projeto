package ifnet;

public class Professor extends Usuario{
	
	private Area area;
	private Disciplina disciplMinistrada;
	
	public Professor(String nome, String prontuario, String senha, Area area, Disciplina disciplMinistrada) {
		super(nome, prontuario, senha);
		this.area = area;
		this.disciplMinistrada = disciplMinistrada;
	}
	
	public Area getArea() {
		return area;
	}
	
	public void setArea(Area area) {
		this.area = area;
	}
	
	public Disciplina getDiciplMinistrada() {
		return disciplMinistrada;
	}

	public Disciplina getDisciplMinistrada() {
		return disciplMinistrada;
	}

	public void setDisciplMinistrada(Disciplina disciplMinistrada) {
		this.disciplMinistrada = disciplMinistrada;
	}
	
	public String toString() {
		return "Nome: " + getNome() + 
				"\nProntu·rio: " + getProntuario() +
				"\n¡rea: " + this.area.getNome() +
				"\nDisciplina: " + this.disciplMinistrada.getNome();
	}
	
	

}
