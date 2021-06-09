package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ConteudoDAO;
import excecoes.GrauConfiabilidadeAtualException;
import excecoes.OpcaoInexistenteException;
import model.ConteudoModel;
import model.UsuarioModel;

public class ConteudoView {
	
	static Scanner leitura = new Scanner(System.in);
	
	public static void exibirConteudo (ArrayList<ConteudoModel> conteudos) {
		
		System.out.println("Conteúdos");
		
		for(ConteudoModel conteudo:conteudos) {
			System.out.println(conteudo);
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
		
		try {
			
			System.out.println("Infome o codigo do contéudo");
			codigo = Integer.parseInt(leitura.nextLine());
			
			if(ConteudoDAO.selecionarConteudo(codigo) == null) {
				throw new OpcaoInexistenteException();
			}
			
			if(ConteudoDAO.selecionarConteudo(codigo).equals(usuarioAtual.getProntuario())) {
				do {
					System.out.println("""
							Você tem certeza que deseja excluir o conteúdo? Essa ação não pode ser desfeita
							1.Sim
							2.Não""");
					opcao = leitura.nextLine();

					switch (opcao) {
						case "1" -> {
							ConteudoDAO.excluirConteudo(codigo);
							System.out.println("Conteúdo excluído");
						}
						case "2" -> System.out.println("Conteúdo não excluído");
						default -> System.out.println("Opção invàlida");
					}
				}while(!opcao.equals("1") && !opcao.equals("2"));
				
			}else System.out.println("Você não tem permissão para excluir este conteúdo, "
					+ "somente o criador do conteúdo pode excluí-lo");
			
		}catch (OpcaoInexistenteException excecao) {
			System.out.println(excecao.getMessage());
		} 
	}
	
}
