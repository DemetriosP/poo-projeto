package ifnet;

import java.util.ArrayList;
import java.util.Scanner;

public class Conteudo {
	
	private String titulo;
	private String tipoConteudo;
	private Usuario publicador;

	static Scanner leitura = new Scanner(System.in);
	
	public Conteudo(String titulo, String tipoConteudo, Usuario publicador) {
		this.titulo = titulo;
		this.tipoConteudo = tipoConteudo;
		this.publicador = publicador;
	}	
	
	public String getTipoConteudo() {
		return tipoConteudo;
	}
	public void setTipoConteudo(String tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
	}
	public Usuario getPublicador() {
		return publicador;
	}
	public void setPublicador(Usuario publicador) {
		this.publicador = publicador;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public static Conteudo inseriConteudo(ArrayList<Conteudo> conteudos, Usuario usuario) {
		
		Conteudo novoConteudo;
		String tipoConteudo, titulo;
		
		System.out.println("Informe o titulo do cont�udo");
		titulo = leitura.nextLine();

		System.out.println("Informe O tipo de conteudo que deseja adicionar");
		tipoConteudo = leitura.nextLine();	
	
		novoConteudo = new Conteudo(titulo, tipoConteudo, usuario);
		
		return novoConteudo;
	}
	
	public static void excluirConteudo(ArrayList<Conteudo> conteudos) {
		
		Conteudo conteudoExcluir;
		int opcao;
		
		conteudoExcluir = Conteudo.exibirConteudos(conteudos);
				
		do {
			
			System.out.println("Voc� tem certeza que deseja excluir o conteudo? "
					+ "Essa a��o n�o pode ser desfeita\n1.Sim\n2.N�o");
			opcao = Integer.parseInt(leitura.nextLine());
			
			switch(opcao) {
			
				case 1:
					conteudos.remove(conteudoExcluir);
					System.out.println("Conteudo exclu�do");
				case 2:
					System.out.println("Conteudo n�o exclu�do");
					break;
				default:
					System.out.println("Op��o inv�lida");
			}
		}while(opcao != 1 && opcao != 2);
	}

	private static Conteudo exibirConteudos(ArrayList<Conteudo> conteudos) {
		
		int posicao;
		Conteudo conteudoEscolhido;
		
		System.out.println("Conte�dos");
		
		for(Conteudo conteudo:conteudos) {
			System.out.println(conteudo.getTitulo());
		}
		
		System.out.println("Informe o n�mero do conteudo desejado: ");
		posicao = Integer.parseInt(leitura.nextLine());
		
		conteudoEscolhido = conteudos.get(posicao);
		
		return conteudoEscolhido;
		
	}

}
