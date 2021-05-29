package ifnet;

public class Aluno extends Usuario{
	
	private String email;
	private Curso curso;
	
	public Aluno(String prontuario) {
		super(prontuario);
	}
	
	public Aluno(String nome, String prontuario, String senha, String email, Curso curso) {
		super(nome, prontuario, senha);
		this.email = email;
		this.curso = curso;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Curso getCurso() {
		return curso;
	}
	
	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "Nome: " + getNome() + 
				"\nProntuário: " + getProntuario() +
				"\nE-mail: " + this.email +
				"\nCurso: " + this.curso.getNome();
	}
	
	
}
