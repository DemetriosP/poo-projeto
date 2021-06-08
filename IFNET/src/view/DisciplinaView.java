package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.DisciplinaDAO;
import excecoes.OpcaoInexistenteException;
import model.DisciplinaModel;

public class DisciplinaView {
	
static Scanner leitura = new Scanner(System.in);
	
	public static DisciplinaModel escolherDisciplina(ArrayList<DisciplinaModel> disciplinas) {
	
		int posicao = 0;
		boolean prosseguir = false;
		
		do {
			
			System.out.println("Disciplinas");
			
			for(DisciplinaModel disciplina:disciplinas) {
				System.out.println(disciplinas.indexOf(disciplina) +". " + disciplina.getNome());
			}
			
			System.out.println("Informe o n�mero da disciplina desejada: ");
			
			try {
				
				posicao = Integer.parseInt(leitura.nextLine());
				
				if(posicao >= disciplinas.size() || posicao < 0) {
					throw new OpcaoInexistenteException();
				}
				
				prosseguir = true;
				
			} catch (NumberFormatException excecao) {
				System.out.println("O valor informado n�o � um n�mero inteiro");
			} catch (OpcaoInexistenteException excecao) {
				System.out.println(excecao.getMessage());
			}
			
		}while(!prosseguir);
		
		return disciplinas.get(posicao);
	}
	
	public static void exibirDisciplina(ArrayList<DisciplinaModel> disciplinas) {
		
		System.out.println("Disciplinas");
		
		for(DisciplinaModel disciplina:disciplinas) {
			System.out.println(disciplina.getNome());
		}
			
	}
	
	public static void cadastrarDisciplina() {
		
		String nome;
		
		System.out.println("Informe O nome da Diciplina que deseja adicionar");
		nome = leitura.nextLine();
		
		DisciplinaDAO.insereDisciplina(new DisciplinaModel(nome));
		
		System.out.println("Disciplina cadastrada");
		
	}
	
	public static void excluirDisciplina(ArrayList<DisciplinaModel> disciplinas) {
		
		String opcao, nome;
		
		if(DisciplinaDAO.contemDisciplina()) {
			
			exibirDisciplina(disciplinas);
			
			System.out.println("Informe o nome da disciplina: ");
			nome = leitura.nextLine();
			
			do {
				System.out.println("Voc� tem certeza que deseja excluir a disciplina? "
						+ "Essa a��o n�o pode ser desfeita\n1.Sim\n2.N�o");
				opcao = leitura.nextLine();
				
				switch(opcao) {
				
					case "1":
						DisciplinaDAO.excluirDisciplina(nome);
						System.out.println("Disciplina exclu�do");
						break;
					case "2":
						System.out.println("Disciplina n�o exclu�do");
						break;
					default:
						System.out.println("Op��o inv�lida");
				}
			}while(!opcao.equals("1") && !opcao.equals("2"));
				
		}else System.out.println("A��o negada, n�o existem disciplinas cadastrados.");
		
	}
		
}
