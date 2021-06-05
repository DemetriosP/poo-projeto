package view;

import java.util.ArrayList;
import java.util.Scanner;

import excecoes.OpcaoInexistenteException;
import model.CursoModel;

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
			
			System.out.println("Informe o número do curso desejado: ");
			
			try {
				
				posicao = Integer.parseInt(leitura.nextLine());
				
				if(posicao >= cursos.size() || posicao < 0) {
					throw new OpcaoInexistenteException();
				}
				
				prosseguir = true;
				
			} catch (NumberFormatException excecao) {
				System.out.println("O valor informado não é um número inteiro");
			} catch (OpcaoInexistenteException excecao) {
				System.out.println(excecao.getMessage());
			}
			
		}while(!prosseguir);
		
		return cursos.get(posicao);
	}

}
