package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.UsuarioDAO;
import excecoes.UsuarioCadastradoException;
import model.UsuarioModel;

public class UsuarioView {
	
	static Scanner leitura = new Scanner(System.in);
	
	public static UsuarioModel cadastrar() {
		
		String prontuario, nome, senha;
		
		System.out.println("Cadastre-se");
		
		try {
			
			System.out.print("Informe o prontuário: ");
			prontuario = leitura.nextLine().toUpperCase();
			
			if(UsuarioDAO.usuarioExiste(prontuario)) {
				throw new UsuarioCadastradoException();
			}
			
			System.out.print("Informe o nome: ");
			nome = leitura.nextLine();
				
			System.out.print("Informe a senha: ");
			senha = leitura.nextLine();
			
			return new UsuarioModel(nome, prontuario, senha);
			
		} catch (UsuarioCadastradoException excecao) {
			System.out.println(excecao.getMessage());
		}
		
		return null;
		
	}
	
	public static UsuarioModel login() {
		
		String prontuario, senha;
		
		UsuarioModel usuario;
		
		System.out.print("Informe o prontuario: ");
		prontuario = leitura.nextLine().toUpperCase();
		
		System.out.print("Informe a senha: ");
		senha = leitura.nextLine();
		
		usuario = new UsuarioModel(prontuario, senha);
		
		if(UsuarioDAO.loginUsuario(usuario)) return usuario;
		else {
			System.out.println("O prontuário ou senha fornecidos não correspondem as "
					+ "informações em nossos registros. Verifique-as e tente novamente.");
		}
		
		return null;
	}
	
	public static void exibirUsuarios(ArrayList<UsuarioModel> usuarios, UsuarioModel usuarioAtual) {
				
		for(UsuarioModel usuario:usuarios) {
			if(usuarios.indexOf(usuario) != usuarios.indexOf(usuarioAtual)){
				System.out.println(usuario.getNome());
			}
		}
			
	}
	
	public static void mudarNome(UsuarioModel usuarioAtual) {
		
		String nome;
		
		System.out.println("Nome Atual: " + usuarioAtual.getNome());
		System.out.print("Novo nome: ");
		nome = leitura.nextLine();
		usuarioAtual.setNome(nome);
		UsuarioDAO.mudarNome(usuarioAtual, nome);
		System.out.println("Nome alterado");
		
	}
	
	public static void mudarSenha(UsuarioModel usuarioAtual) {
		
		String senha;
		
		System.out.println("Senha Atual: " + usuarioAtual.getSenha());
		System.out.print("Novo senha: ");
		senha = leitura.nextLine();
		usuarioAtual.setSenha(senha);
		UsuarioDAO.mudarSenha(usuarioAtual, senha);
		System.out.println("Senha alterado");
		
	}
	
	public static boolean excluirConta(UsuarioModel usuarioAtual) {
		
		boolean excluido = false;
		
		String opcao;
		
		do {
			
			System.out.println("""
					Você tem certeza que deseja excluir a sua conta? Essa ação não pode ser desfeita
					1.Sim
					2.Não""");
			opcao = leitura.nextLine();

			switch (opcao) {
				case "1" -> {
					if (AlunoDAO.eAluno(usuarioAtual.getProntuario())) AlunoDAO.excluirAluno(usuarioAtual);
					else ProfessorDAO.excluirProfessor(usuarioAtual);
					excluido = true;
					System.out.println("Conta excluída");
				}
				case "2" -> System.out.println("Conta não excluída");
				default -> System.out.println("Opção invàlida");
			}
			
		}while(!opcao.equals("1") && !opcao.equals("2"));
		
		return excluido;
	}
		
}
