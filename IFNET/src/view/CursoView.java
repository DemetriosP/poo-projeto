package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.CursoDAO;
import dao.DisciplinaDAO;
import excecoes.OpcaoInexistenteException;
import model.CursoModel;
import model.DisciplinaModel;

public class CursoView {
	
	static Scanner leitura = new Scanner(System.in);
	
	public static CursoModel escolherCurso(ArrayList<CursoModel> cursos) {
		
		int posicao = 0;
		boolean prosseguir = false;
		
		do {
			
			System.out.println("Cursos");
			
			for(CursoModel curso:cursos) {
				System.out.println(cursos.indexOf(curso) +". " + curso.getNome());
			}
			
			System.out.println("Informe o n�mero do curso desejado: ");
			
			try {
				
				posicao = Integer.parseInt(leitura.nextLine());
				
				if(posicao >= cursos.size() || posicao < 0) {
					throw new OpcaoInexistenteException();
				}
				
				prosseguir = true;
				
			} catch (NumberFormatException excecao) {
				System.out.println("O valor informado n�o � um n�mero inteiro");
			} catch (OpcaoInexistenteException excecao) {
				System.out.println(excecao.getMessage());
			}
			
		}while(!prosseguir);
		
		return cursos.get(posicao);
	}
	
	public static void exibirCurso(ArrayList<CursoModel> cursos) {
		
		System.out.println("Curso");
		
		for(CursoModel curso:cursos) {
			System.out.println(cursos.indexOf(curso) +". " + curso.getNome());
		}
			
	}
	
	public static void excluirCurso() {
		
		String opcao, nome;
		
		if(CursoDAO.contemCurso()) {
			
			exibirCurso(CursoDAO.selecionarCursos());
			
			System.out.println("Informe o nome do curso: ");
			nome = leitura.nextLine();
			
			do {
				
				System.out.println("Voc� tem certeza que deseja excluir o curso? "
						+ "Essa a��o n�o pode ser desfeita\n1.Sim\n2.N�o");
				opcao = leitura.nextLine();
				
				switch(opcao) {
				
					case "1":
						CursoDAO.excluirCurso(nome);
						System.out.println("Curso exclu�do");
						break;
					case "2":
						System.out.println("Curso n�o exclu�do");
						break;
					default:
						System.out.println("Op��o inv�lida");
				}
			}while(!opcao.equals("1") && !opcao.equals("2"));

		}else System.out.println("A��o negada, n�o existem cursos cadastrados.");
		
	}
	
	public static CursoModel cadastrarCurso() {
		
		String nome, opcao;
		
		int semestre = 0, semestres = 0;
		
		boolean prosseguir = false;
		
		CursoModel curso;
		
		DisciplinaModel disciplina;
		
		System.out.println("Cadastrar Curso");
		
		System.out.println("Informe o nome do curso: ");
		nome = leitura.nextLine();

		do{

			try{

			System.out.println("Informe a quantidade de semestres do curso: ");
			semestres = Integer.parseInt(leitura.nextLine());

			prosseguir = true;

			}catch(NumberFormatException excecoes) {
				System.out.println("O valor informado n�o � um n�mero inteiro");
			}

		}while(!prosseguir);

		prosseguir = false;
		
		curso = new CursoModel(nome, semestres);
		
		do {
			
			disciplina = DisciplinaView.escolherDisciplina(DisciplinaDAO.selecionarDisciplinas());

			do{

				try{

				System.out.println("Informe o semetre da disciplina");
				semestre = Integer.parseInt(leitura.nextLine());

				if(semestre > semestres || semestre  < 0) {
						throw new OpcaoInexistenteException();
					}

				prosseguir = true;

				}catch(NumberFormatException excecoes) {
					System.out.println("O valor informado n�o � um n�mero inteiro");

				}catch (OpcaoInexistenteException excecao) {
					System.out.println(excecao.getMessage());
				}

			}while(!prosseguir);

			prosseguir = false;

			curso.setDisciplinasPorSemestre(semestre, disciplina);
			
			do {
				System.out.println("Deseja cadastrar outra disciplina no curso?\n1. Sim\n2. N�o");
				opcao = leitura.nextLine();
				
				if(!opcao.equals("1") && !opcao.equals("2")) System.out.println("Op��o inv�lida");
					
			}while(!opcao.equals("1") && !opcao.equals("2"));
														
		}while(opcao.equals("1"));
		
		return curso;
		
	}

}
