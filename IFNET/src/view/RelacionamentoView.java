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
	
	public static UsuarioModel relacionarUsuario(UsuarioModel usuarioAtual) {
		
		String nome, prontuario;
		
		System.out.println("Infome o nome do usuário: ");
		nome = leitura.nextLine();
		
		UsuarioView.exibirUsuarios(UsuarioDAO.pesquisarUsuario(nome), usuarioAtual);
		
		try {
			
			System.out.println("Informe o prontuario do usuário: ");
			prontuario = leitura.nextLine().toUpperCase();
			
			if(!UsuarioDAO.usuarioExiste(prontuario)) {
				throw new OpcaoInexistenteException();
			}
			
			if(RelacionamentoDAO.eRelacionado(usuarioAtual.getProntuario(), prontuario)) 
				System.out.println("O usuário atual e o usuário informado já estão relacionados");
			else return UsuarioDAO.selecionaUsuario(prontuario);
			
		}catch (OpcaoInexistenteException excecao) {
			System.out.println(excecao.getMessage());
		}
		
		return null;
	}
	
	public static void alterarGrauRelacionamento(UsuarioModel usuarioAtual) {

		int novoGrau;

		String prontuario, grauAtual;
		String[] grauConf = {"Conhecidos", "Amigos", "Melhores Amigos"};
		
		try {
		
			System.out.println("Informe o prontuario do usuário que deseja alterar o grau de confiabilidade: ");
			prontuario = leitura.nextLine().toUpperCase();
		
			if(!RelacionamentoDAO.eRelacionado(usuarioAtual.getProntuario(), prontuario)) {
				throw new UsuarioNaoRelacionadoException();
			}
			
			grauAtual = RelacionamentoModel.getGrauRelacionamento(usuarioAtual, prontuario);
			
			System.out.println("Grau de Confiabilidade Atual: " + grauAtual);
			
			System.out.println("Graus de confiabilidade\n0.Conhecidos\n1.Amigos\n2.Amigos Próximos");
			
			System.out.print("Informe o número do grau de confiabilidade para o qual a relação será alterada: ");
			novoGrau = Integer.parseInt(leitura.nextLine());

			if(novoGrau < 0 || novoGrau > 2) {
				throw new OpcaoInexistenteException();
			}
			
			if(grauAtual.equals(grauConf[novoGrau])) {
				throw new GrauConfiabilidadeAtualException();
			}
			
			RelacionamentoDAO.alterarGrauRelacionamento(grauAtual, usuarioAtual.getProntuario(), prontuario);
			
			System.out.println("Grau de Confiabilidade Alterado");
			
		} catch (NumberFormatException excecao) {
			System.out.println("O valor informado não é um número inteiro");
		} catch (GrauConfiabilidadeAtualException | OpcaoInexistenteException | UsuarioNaoRelacionadoException excecao) {
			System.out.println(excecao.getMessage());
		}

	}
	
	public static void exibirRelacionamentos(UsuarioModel usuarioAtual) {
		
		for (Map.Entry<String , ArrayList<UsuarioModel>> mapa : usuarioAtual.getRelacionamento().getGrauUsuario().entrySet()) {
			for(UsuarioModel usuarios:mapa.getValue()) {
				System.out.println(usuarios);
			}		
		}		
	}
	
	public static void excluirRelacionamento(UsuarioModel usuarioAtual) {
		
		String prontuario;
		int opcao = 0;
		
		try {

			System.out.println("Informe o prontuario do usuário que deseja excluir o relacionamento: ");
			prontuario = leitura.nextLine();

			if(!RelacionamentoDAO.eRelacionado(usuarioAtual.getProntuario(), prontuario)) {
				throw new UsuarioNaoRelacionadoException();
			}

			System.out.println("""
					Você tem certeza que deseja excluir o relacionamento?\s
					1.Sim
					2.Não""");
			opcao = Integer.parseInt(leitura.nextLine());
			
			if(opcao <= 0 || opcao > 2) {
				throw new OpcaoInexistenteException();
			} else if (opcao == 1) {
				RelacionamentoDAO.excluirRelacionamento(usuarioAtual.getProntuario(), prontuario);
				System.out.println("Relacionamento excluído");
			} else System.out.println("Relacionamento não excluído");

		}catch (UsuarioNaoRelacionadoException | OpcaoInexistenteException excecao) {
			System.out.println(excecao.getMessage());
		}

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
