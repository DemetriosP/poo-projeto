package model;

import java.util.ArrayList;

public class UsuarioModel {
	
	private String nome;
	private String prontuario;
	private String senha;
	private RelacionamentoModel relacionamento;
	
	public UsuarioModel(String prontuario) {
		this.prontuario = prontuario;
	}
	
	public UsuarioModel(String prontuario, String senha) {
		this.prontuario = prontuario;
		this.senha = senha;
	}
	
	public UsuarioModel(String nome, String prontuario, String senha) {
		this.nome = nome;
		this.prontuario = prontuario;
		this.senha = senha;
		this.relacionamento = new RelacionamentoModel();
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getProntuario() {
		return prontuario;
	}
	
	public void setProntuario(String prontuario) {
		this.prontuario = prontuario;
	}
	
	public String getSenha() {
		return senha;	
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public RelacionamentoModel getRelacionamento() {
		return relacionamento;
	}

	public void setRelacionamento(RelacionamentoModel relacionamento) {
		this.relacionamento = relacionamento;
	}
	
	public static ArrayList<UsuarioModel> pesquisarUsuario(ArrayList<UsuarioModel> usuarios, String nome) {
	
		ArrayList<UsuarioModel> usuariosPesquisados = new ArrayList<>();
		
		for(UsuarioModel usuario:usuarios) 
			if(usuario.getNome().toLowerCase().contains(nome.toLowerCase())) 
				usuariosPesquisados.add(usuario);
		
		return usuariosPesquisados;
	}
	
	@Override 
	public boolean equals(Object usuario) {
		
		if(this.nome.equals(((UsuarioModel) usuario).getNome()))
			return this.prontuario.equals(((UsuarioModel) usuario).getProntuario());
		return false;
		
	}

	@Override
	public String toString() {
		return this.nome;
	}

}
