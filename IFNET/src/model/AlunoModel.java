package model;

public class AlunoModel extends UsuarioModel{
	
	private String email;
	private CursoModel curso;
	
	public AlunoModel(String prontuario) {
		super(prontuario);
	}
	
	public AlunoModel(String nome, String prontuario, String senha, String email, CursoModel curso) {
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
	
	public CursoModel getCurso() {
		return curso;
	}
	
	public void setCurso(CursoModel curso) {
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
