package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.UsuarioDAO;
import excecoes.UsuarioCadastradoException;
import model.UsuarioModel;

public class UsuarioView {
	
	static Scanner leitura = new Scanner(System.in);
	
	public static UsuarioModel cadastrar() {
		
		String prontuario = "", nome = "", senha = "";
		
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
	
	
		
}
