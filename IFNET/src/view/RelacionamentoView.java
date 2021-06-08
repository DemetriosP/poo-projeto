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
		
		System.out.println("Infome o nome do usuário: ");
		nome = leitura.nextLine();
		
		UsuarioView.exibirUsuarios(AlunoDAO.pesquisarAlunos(nome), usuarioAtual);
		UsuarioView.exibirUsuarios(ProfessorDAO.pesquisarProfessores(nome), usuarioAtual);
		
		System.out.println("Informe o prontuario do usuário: ");
		prontuario = leitura.nextLine();
		
		if(RelacionamentoDAO.eRelacionado(nome, prontuario)) {
			System.out.println("O usuário atual e o usuário informado já estão relacionados");
		}else {
			RelacionamentoModel.relacionarUsuario(usuarioAtual, UsuarioDAO.selecionaUsuario(prontuario));
			RelacionamentoDAO.inserirRelacionamento(usuarioAtual);
			System.out.println("O relacionamento foi criado");
		}
		
	}
	
	public static void alterarGrauRelacionamento(UsuarioModel usuarioAtual) {
		
		boolean prosseguir = false;

		int novoGrau;

		String prontuario, grauAtual = null;
		String[] grauConf = {"Conhecidos", "Amigos", "Melhores Amigos"};

		usuarioAtual.setRelacionamento(RelacionamentoDAO.selecionarRelacionamento(usuarioAtual));
		
		exibirRelacionamentos(usuarioAtual);
		
		System.out.println("Informe o prontuario do usuário que deseja alterar o grau de confiabilidade: ");
		prontuario = leitura.nextLine();
		
		do{

			try {
				
				grauAtual = RelacionamentoModel.getGrauRelacionamento(usuarioAtual, prontuario);
				
				System.out.println("Grau de Confiabilidade Atual: " + grauAtual);
				
				System.out.println("Graus de confiabilidade\n0.Conhecidos\n1.Amigos\n2.Amigos Próximos");
				
				System.out.print("Informe o número do grau de confiabilidade para o qual a relação será alterada: ");
				novoGrau = Integer.parseInt(leitura.nextLine());

				if(grauAtual.equals(grauConf[novoGrau])) {
					throw new GrauConfiabilidadeAtualException();
				}

				if(novoGrau < 0 || novoGrau > 2) {
					throw new OpcaoInexistenteException();
				}

				prosseguir = true;
				
			} catch (NumberFormatException excecao) {
				System.out.println("O valor informado não é um número inteiro");
			} catch (GrauConfiabilidadeAtualException | OpcaoInexistenteException excecao) {
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
		
		String prontuario, opcao = "";
		
		usuarioAtual.setRelacionamento(RelacionamentoDAO.selecionarRelacionamento(usuarioAtual));
		
		exibirRelacionamentos(usuarioAtual);
		
		do {

			try {

				System.out.println("Informe o prontuario do usuário que deseja excluir o relacionamento: ");
				prontuario = leitura.nextLine();

				if(RelacionamentoDAO.eRelacionado(usuarioAtual.getProntuario(), prontuario)) {
					throw new UsuarioNaoRelacionadoException();
				}

				System.out.println("""
						Você tem certeza que deseja excluir o relacionamento?\s
						1.Sim
						2.Não""");
				opcao = leitura.nextLine();

				switch (opcao) {
					case "1" -> {
						RelacionamentoDAO.excluirRelacionamento(usuarioAtual.getProntuario(), prontuario);
						System.out.println("Relacionamento excluído");
					}
					case "2" -> System.out.println("Relacionamento não excluído");
					default -> System.out.println("Opção invàlida");
				}

			}catch (UsuarioNaoRelacionadoException excecao) {
				System.out.println(excecao.getMessage());
			}

		}while(!opcao.equals("1") && !opcao.equals("2"));
		
	}
	
	public static void usuarioMaisRelacionados() {
		
		System.out.println("TOP 10 Usuários mais relacionados");
		
		for(String[] dado:RelacionamentoDAO.usuariosMaisRelacionados()) {
			
			if(AlunoDAO.eAluno(dado[0])) System.out.print(AlunoDAO.selecionarAluno(dado[0]).getNome());
			else System.out.print(ProfessorDAO.selecionarProfessor(dado[0]).getNome());
				
			System.out.println(" - Relacionamentos: " + dado[1]);
		}
		
	}
	

}
