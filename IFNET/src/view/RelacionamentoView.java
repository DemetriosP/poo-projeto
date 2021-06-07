package view;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.RelacionamentoDAO;
import dao.UsuarioDAO;
import excecoes.GrauConfiabilidadeAtualException;
import excecoes.OpcaoInexistenteException;
import excecoes.UsuarioNaoRelacionadoException;
import model.RelacionamentoModel;
import model.UsuarioModel;

public class RelacionamentoView {
	
	static Scanner leitura = new Scanner(System.in);
	
	public static void relacionarUsuario(UsuarioModel usuarioAtual) {
		
		String nome, prontuario;
		
		System.out.println("Infome o nome do usu�rio: ");
		nome = leitura.nextLine();
		
		UsuarioView.exibirUsuarios(AlunoDAO.pesquisarAlunos(nome), usuarioAtual);
		UsuarioView.exibirUsuarios(ProfessorDAO.pesquisarProfessores(nome), usuarioAtual);
		
		System.out.println("Informe o prontuario do usu�rio: ");
		prontuario = leitura.nextLine();
		
		if(RelacionamentoDAO.eRelacionado(nome, prontuario)) {
			System.out.println("O usu�rio atual e o usu�rio informado j� est�o relacionados");
		}else {
			RelacionamentoModel.relacionarUsuario(usuarioAtual, UsuarioDAO.selecionaUsuario(prontuario));
			RelacionamentoDAO.inserirRelacionamento(usuarioAtual);
			System.out.println("O relacionamento foi criado");
		}
		
	}
	
	public static void alterarGrauRelacionamento(UsuarioModel usuarioAtual) {
		
		boolean prosseguir = false;
		
		String prontuario, grauAtual = null, grauConf[] = {"Conhecidos", "Amigos", "Melhores Amigos"};
		
		int novoGrau;
		
		usuarioAtual.setRelacionamento(RelacionamentoDAO.selecionarRelacionamento(usuarioAtual));
		
		exibirRelacionamentos(usuarioAtual);
		
		System.out.println("Informe o prontuario do usu�rio que deseja alterar o grau de confiabilidade: ");
		prontuario = leitura.nextLine();
		
		do{

			try {
				
				grauAtual = RelacionamentoModel.getGrauRelacionamento(usuarioAtual, prontuario);
				
				System.out.println("Grau de Confiabilidade Atual: " + grauAtual);
				
				System.out.println("Graus de confiabilidade\n0.Conhecidos\n1.Amigos\n2.Amigos Pr�ximos");
				
				System.out.print("Informe o n�mero do grau de confiabilidade para o qual a rela��o ser� alterada: ");
				novoGrau = Integer.parseInt(leitura.nextLine());
				
				if(grauAtual.equals(grauConf[novoGrau])) {
					throw new GrauConfiabilidadeAtualException();
				}
				
				if(novoGrau > 2 || novoGrau < 0) {
					throw new OpcaoInexistenteException();
				}

				prosseguir = true;
				
			} catch (NumberFormatException excecao) {
				System.out.println("O valor informado n�o � um n�mero inteiro");
			} catch (GrauConfiabilidadeAtualException excecao) {
				System.out.println(excecao.getMessage());
			} catch (OpcaoInexistenteException excecao) {
				System.out.println(excecao.getMessage());
			}

		}while(!prosseguir);
		
		RelacionamentoDAO.alterarGrauRelacionamento(grauAtual, usuarioAtual.getProntuario(), prontuario);
		
		System.out.println("Grau de Confiabilidade Alterado");
		
	}
	
	public static void exibirRelacionamentos(UsuarioModel usuarioAtual) {
		
		for (Map.Entry<String , ArrayList<UsuarioModel>> mapa : usuarioAtual.getRelacionamento().getGrauUsuario().entrySet()) {
			for(UsuarioModel usuarios:mapa.getValue()) {
				System.out.println(usuarios);
			}		
		}		
	}
	
	public static void excluirRelacionamento(UsuarioModel usuarioAtual) {
		
		String prontuario, opcao = null;
		
		usuarioAtual.setRelacionamento(RelacionamentoDAO.selecionarRelacionamento(usuarioAtual));
		
		exibirRelacionamentos(usuarioAtual);
		
		do {
			
			try {
				
				System.out.println("Informe o prontuario do usu�rio que deseja excluir o relacionamento: ");
				prontuario = leitura.nextLine();
				
				if(RelacionamentoDAO.eRelacionado(usuarioAtual.getProntuario(), prontuario)) {
					throw new UsuarioNaoRelacionadoException();
				}
				
				System.out.println("Voc� tem certeza que deseja excluir o relacionamento? "
						+ "\n1.Sim\n2.N�o");
				opcao = leitura.nextLine();
				
				switch(opcao) {
				
					case "1":
						RelacionamentoDAO.excluirRelacionamento(usuarioAtual.getProntuario(), prontuario);
						System.out.println("Relacionamento exclu�do");
						break;
					case "2":
						System.out.println("Relacionamento n�o exclu�do");
						break;
					default:
						System.out.println("Op��o inv�lida");
				}
				
			}catch (UsuarioNaoRelacionadoException excecao) {
				System.out.println(excecao.getMessage());
			}
			
		}while(!opcao.equals("1") && !opcao.equals("2"));
		
	}
	
	public static void usuarioMaisRelacionados() {
		
		System.out.println("TOP 10 Usu�rios mais relacionados");
		
		for(String[] dado:RelacionamentoDAO.usuariosMaisRelacionados()) {
			
			if(AlunoDAO.eAluno(dado[0])) System.out.print(AlunoDAO.selecionarAluno(dado[0]).getNome());
			else System.out.print(ProfessorDAO.selecionarProfessor(dado[0]).getNome());
				
			System.out.println(" - Relacionamentos: " + dado[1]);
		}
		
	}
	

}
