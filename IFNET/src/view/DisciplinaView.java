package view;

import java.util.ArrayList;
import java.util.Scanner;

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
			
			System.out.println("Informe o número da disciplina desejada: ");
			
			try {
				
				posicao = Integer.parseInt(leitura.nextLine());
				
				if(posicao >= disciplinas.size() || posicao < 0) {
					throw new OpcaoInexistenteException();
				}
				
				prosseguir = true;
				
			} catch (NumberFormatException excecao) {
				System.out.println("O valor informado não é um número inteiro");
			} catch (OpcaoInexistenteException excecao) {
				System.out.println(excecao.getMessage());
			}
			
		}while(!prosseguir);
		
		return disciplinas.get(posicao);
	}
	
	

}
