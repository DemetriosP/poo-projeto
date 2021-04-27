package ifnet;

import java.util.ArrayList;
import java.util.Scanner;

public class Disciplina {
	
	private String nome;
	
	static Scanner leitura = new Scanner(System.in);
	
	public Disciplina(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public static ArrayList<Disciplina> cadastrarDisciplina(ArrayList<Disciplina> disciplinas) {
		
		ArrayList<Disciplina> novasDisciplinas = new ArrayList<Disciplina>();
		String nome;
		
		int opc = 0;
		
		//Looop para cadastrar mais de uma Diciplina		
		do {

			System.out.println("Informe O nome da Diciplina que deseja adicionar");
			nome = leitura.nextLine();
			
			System.out.println("Deseja cadastrar outra disciplina no curso?\n1. Sim\n2. N�o");
			opc = Integer.parseInt(leitura.nextLine());
			
			novasDisciplinas.add(new Disciplina(nome));

		}while( opc != 1 );
			
		return novasDisciplinas;

	}
	
	//excluir a disciplina
	public static void excluirDisciplia(ArrayList<Disciplina> disciplinas) {
		
		Disciplina disciplinaExcluir;
		int opcao;
		
		disciplinaExcluir = Disciplina.exibirDisciplinas(disciplinas);
				
		do {
			
			System.out.println("Voc� tem certeza que deseja excluir o curso? "
					+ "Essa a��o n�o pode ser desfeita\n1.Sim\n2.N�o");
			opcao = Integer.parseInt(leitura.nextLine());
			
			switch(opcao) {
			
				case 1:
					disciplinas.remove(disciplinaExcluir);
					System.out.println("Disciplina exclu�do");
				case 2:
					System.out.println("Disciplina n�o exclu�do");
					break;
				default:
					System.out.println("Op��o inv�lida");
			}
		}while(opcao != 1 && opcao != 2);
	}
	
	public static Disciplina exibirDisciplinas(ArrayList<Disciplina> disciplinas) {
		
		int posicao;
		Disciplina disciplinaEscolhida;
		
		System.out.println("Disciplinas");
		
		for(Disciplina disciplina:disciplinas) {
			posicao = disciplinas.indexOf(disciplina);
			System.out.println(posicao + ". " + disciplina.getNome());
		}
		
		System.out.println("Informe o n�mero da disciplina desejada: ");
		posicao = Integer.parseInt(leitura.nextLine());
		
		disciplinaEscolhida = disciplinas.get(posicao);
		
		return disciplinaEscolhida;
		
	}

}
