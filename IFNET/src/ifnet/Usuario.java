package ifnet;

import java.util.ArrayList;

public abstract class Usuario {
	
	private String nome;
	private String prontuario;
	private String senha;
	private Relacionamento relacionamento;
	
	public Usuario(String prontuario) {
		this.prontuario = prontuario;
	}
	
	public Usuario(String nome, String prontuario, String senha) {
		this.nome = nome;
		this.prontuario = prontuario;
		this.senha = senha;
		this.relacionamento = new Relacionamento();
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
	
	public Relacionamento getRelacionamento() {
		return relacionamento;
	}

	public void setRelacionamento(Relacionamento relacionamento) {
		this.relacionamento = relacionamento;
	}
	
	//faz o login do usuário, recebendo o array de usuários
	public static Usuario loginUsuario(ArrayList<Usuario> usuarios, String prontuario, String senha) {
		
		//verifica se o prontuario e a senha constam no cadastro de algum usuário
		for(Usuario usuario: usuarios) {
			if(usuario.getProntuario().equals(prontuario)) {
				if(usuario.getSenha().equals(senha)) {
					return usuario;
				}
			}		
		}
		return null;
	}
	
	public static ArrayList<Usuario> pesquisarUsuario(ArrayList<Usuario> usuarios, String nome) {
	
		ArrayList<Usuario> usuariosPesquisados = new ArrayList<Usuario>();
		
		for(Usuario usuario:usuarios) 
			if(usuario.getNome().toLowerCase().contains(nome.toLowerCase())) 
				usuariosPesquisados.add(usuario);
		
		return usuariosPesquisados;
	}
	
	public static boolean usuarioExistente(ArrayList<Usuario> usuarios, String prontuario) {
		
		for(Usuario usuario:usuarios) {
			if(usuario.getProntuario().equals(prontuario)) return true;
		}
		
		return false;
		
	}
	
	@Override public boolean equals(Object usuario) {
		
		if(this.nome == ((Usuario) usuario).getNome())
			if(this.prontuario == ((Usuario) usuario).getProntuario())
				return true;
		return false;
		
	}

	@Override
	public String toString() {
		return null;
	}

}
