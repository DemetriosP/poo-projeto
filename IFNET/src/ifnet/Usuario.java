package ifnet;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Usuario {
	
	private String nome;
	private String prontuario;
	private String senha;
	
	static Scanner leitura = new Scanner(System.in);
	
	public Usuario(String nome, String prontuario, String senha) {
		this.nome = nome;
		this.prontuario = prontuario;
		this.senha = senha;
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
	
	/*
	realiza o cadastro do usu�rio, a partir do inser��o das 
	informa��es do usu�rio, dependendo do tipo do usu�rio
	*/
	public static Usuario cadastrarUsuario() {
		
		Usuario novoUsuario;
		String nome, prontuario, senha, area, disciplina, email, curso;
		int tipo = 0;
		
		do {
			System.out.println("Voc� � Aluno ou Professor?\n1 - Aluno\n2 - Professor");
			tipo = Integer.parseInt(leitura.nextLine());
		} while(tipo != 1 && tipo != 2);
		
		System.out.println("Informe o nome: ");
		nome = leitura.nextLine();
		
		System.out.println("Informe o prontu�rio: ");
		prontuario = leitura.nextLine();
		
		System.out.println("Informe a senha: ");
		senha = leitura.nextLine();
		
		if(tipo == 2) {
			
			System.out.println("Informe a �rea: ");
			area = leitura.nextLine();
			
			System.out.println("Informe a disciplina: ");
			disciplina = leitura.nextLine();
			
			novoUsuario = new Professor(nome, prontuario, senha,
					new Area(area), new Disciplina(disciplina));
		} else {
			
			System.out.println("Informe o e-mail: ");
			email = leitura.nextLine();
			
			System.out.println("Informe o curso: ");
			curso = leitura.nextLine();
			
			novoUsuario = new Aluno(nome, prontuario, senha, email, new Curso(curso));
		}
		
		return novoUsuario;
	}
	
	//excluir o usu�rio a partir do recebimento do array de usu�rios e o usu�rio a ser excluido
	public static boolean excluirUsuario(ArrayList<Usuario> usuarios, Usuario usuario) {	
		return usuarios.remove(usuario);
	}
	
	//faz o login do usu�rio, recebendo o array de usu�rios
	public static Usuario loginUsuario(ArrayList<Usuario> usuarios) {
		
		String prontuario, senha;
		
		//recebe o prontu�rio
		System.out.println("Informe o prontuario: ");
		prontuario = leitura.nextLine();
		
		//recebe a senha
		System.out.println("Informe a senha: ");
		senha = leitura.nextLine();
		
		//verifica se o prontuario e a senha constam no cadastro de algum usu�rio
		for(Usuario usuario: usuarios) {
			if(usuario.getProntuario().equals(prontuario)) {
				if(usuario.getSenha().equals(senha)) {
					return usuario;
				}
			}		
		}
		return null;
	}
	
}
