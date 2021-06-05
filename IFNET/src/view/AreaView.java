package view;

import java.util.ArrayList;
import java.util.Scanner;

import excecoes.OpcaoInexistenteException;
import model.AreaModel;

public class AreaView {
	
	static Scanner leitura = new Scanner(System.in);
	
	public static AreaModel escolherArea(ArrayList<AreaModel> areas) {
	
		int posicao = 0;
		boolean prosseguir = false;
		
		do {
			
			System.out.println("�reas");
			
			for(AreaModel area:areas) {
				System.out.println(areas.indexOf(area) +". " + area.getNome());
			}
			
			System.out.println("Informe o n�mero do �rea desejado: ");
			
			try {
				
				posicao = Integer.parseInt(leitura.nextLine());
				
				if(posicao >= areas.size() || posicao < 0) {
					throw new OpcaoInexistenteException();
				}
				
				prosseguir = true;
				
			} catch (NumberFormatException excecao) {
				System.out.println("O valor informado n�o � um n�mero inteiro");
			} catch (OpcaoInexistenteException excecao) {
				System.out.println(excecao.getMessage());
			}
			
		}while(!prosseguir);
		
		return areas.get(posicao);
	}

}
