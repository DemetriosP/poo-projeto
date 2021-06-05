package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ConteudoDAO;
import model.ConteudoModel;
import model.UsuarioModel;

public class ConteudoView {
	
	static Scanner leitura = new Scanner(System.in);
	
	public static void exibirConteudo (ArrayList<ConteudoModel> conteudos) {
		
		System.out.println("Conteúdos");
		
		for(ConteudoModel conteudo:conteudos) {
			System.out.println(conteudo.getTitulo());
		}
			
	}
	
	public static ConteudoModel cadastrarConteudo(UsuarioModel usuarioAtual) {
		
		String titulo, tipo;
		
		System.out.println("Informe o titulo do conteúdo");
		titulo = leitura.nextLine();

		System.out.println("Informe o tipo de conteúdo que deseja adicionar");
		tipo = leitura.nextLine();	
	
		System.out.println("Conteúdo publicado");
		
		return new ConteudoModel(titulo, tipo, usuarioAtual);
	}
	
	public static void excluirConteudo(ArrayList<ConteudoModel> conteudos, UsuarioModel usuarioAtual) {
		
		int codigo;
		String opcao;
		
		System.out.println("Infome o codigo do contéudo");
		codigo = Integer.parseInt(leitura.nextLine());
		
		if(ConteudoDAO.selecionarConteudo(codigo) == usuarioAtual.getProntuario()) {
			
			do {
				
				System.out.println("Você tem certeza que deseja excluir o conteúdo? "
						+ "Essa ação não pode ser desfeita\n1.Sim\n2.Não");
				opcao = leitura.nextLine();
				
				switch(opcao) {
				
					case "1":
						ConteudoDAO.excluirConteudo(codigo);
						System.out.println("Conteúdo excluído");
						break;
					case "2":
						System.out.println("Conteúdo não excluído");
						break;
					default:
						System.out.println("Opção invàlida");
				}
			}while(!opcao.equals("1") && !opcao.equals("2"));
			
		}else {
			System.out.println("Você não tem permissão para excluir este conteúdo, somente o criador do conteúdo pode excluí-lo");
		}
		
	}
	
	

}
