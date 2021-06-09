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
		
		System.out.println("Conte�dos");
		
		for(ConteudoModel conteudo:conteudos) {
			System.out.println(conteudo);
		}
			
	}
	
	public static ConteudoModel cadastrarConteudo(UsuarioModel usuarioAtual) {
		
		String titulo, tipo;
		
		System.out.println("Informe o titulo do conte�do");
		titulo = leitura.nextLine();

		System.out.println("Informe o tipo de conte�do que deseja adicionar");
		tipo = leitura.nextLine();	
	
		System.out.println("Conte�do publicado");
		
		return new ConteudoModel(titulo, tipo, usuarioAtual);
	}
	
	public static void excluirConteudo(ArrayList<ConteudoModel> conteudos, UsuarioModel usuarioAtual) {
		
		int codigo;
		String opcao;
		
		try {
			
			System.out.println("Infome o codigo do cont�udo");
			codigo = Integer.parseInt(leitura.nextLine());
			
			if(ConteudoDAO.selecionarConteudo(codigo) == null) {
				throw new OpcaoInexistenteException();
			}
			
			if(ConteudoDAO.selecionarConteudo(codigo).equals(usuarioAtual.getProntuario())) {
				do {
					System.out.println("""
							Voc� tem certeza que deseja excluir o conte�do? Essa a��o n�o pode ser desfeita
							1.Sim
							2.N�o""");
					opcao = leitura.nextLine();

					switch (opcao) {
						case "1" -> {
							ConteudoDAO.excluirConteudo(codigo);
							System.out.println("Conte�do exclu�do");
						}
						case "2" -> System.out.println("Conte�do n�o exclu�do");
						default -> System.out.println("Op��o inv�lida");
					}
				}while(!opcao.equals("1") && !opcao.equals("2"));
				
			}else System.out.println("Voc� n�o tem permiss�o para excluir este conte�do, "
					+ "somente o criador do conte�do pode exclu�-lo");
			
		}catch (OpcaoInexistenteException excecao) {
			System.out.println(excecao.getMessage());
		} 
	}
	
}
