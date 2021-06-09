package model;

import java.util.ArrayList;

public class GrupoModel {
	
	private int codigo;
	private String nome;
	private DisciplinaModel disciplina;
	private ArrayList<UsuarioModel> usuariosGrupo = new ArrayList<>();
	private ProfessorModel criador;
	private String tipo;
	
	public GrupoModel(String nome, DisciplinaModel disciplina, ProfessorModel usuarioAtual, String tipo) {
		this.nome = nome;
		this.disciplina = disciplina;
		this.criador = usuarioAtual;
		this.tipo = tipo;
		this.usuariosGrupo.add(usuarioAtual);
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public DisciplinaModel getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(DisciplinaModel disciplina) {
		this.disciplina = disciplina;
	}
	
	public ArrayList<UsuarioModel> getUsuariosGrupo() {
		return usuariosGrupo;
	}

	public void setUsuariosGrupo(ArrayList<UsuarioModel> usuario) {
		this.usuariosGrupo = usuario;
	}
	
	public UsuarioModel getUsuarioGrupo(UsuarioModel usuario) {
		return usuariosGrupo.get(usuariosGrupo.indexOf(usuario));
	}

	public void setUsuarioGrupo(UsuarioModel usuario) {
		this.usuariosGrupo.add(usuario);
	}
	
	public UsuarioModel getCriador() {
		return criador;
	}
	
	public void setCriador(ProfessorModel criador) {
		this.criador = criador;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Código: " + this.codigo + 
				"\nNome: " + this.nome + 
				"\nDisciplina: " + this.disciplina.getNome() + 
				"\nQuantidade de Usuários: " + this.usuariosGrupo.size() + 
				"\nCriador: " + this.criador.getNome() + 
				"\nTipo: " + this.tipo;
	}

}
