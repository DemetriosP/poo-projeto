package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.DisciplinaDAO;
import dao.GrupoDAO;
import excecoes.GrupoInexistenteException;
import model.DisciplinaModel;
import model.GrupoModel;
import model.ProfessorModel;
import model.UsuarioModel;

public class GrupoView {
	
	static Scanner leitura = new Scanner(System.in);
	
	public static void exibirGrupo(ArrayList<GrupoModel> grupos) {
	
		System.out.println("Grupos");
		
		for(GrupoModel grupo:grupos) {
			System.out.println(grupo);
		}
			
	}
	
	public static void consultarGrupoPesqPorDisciplina() {
		
		String nome;
		
		ArrayList<GrupoModel> grupos;
		
		System.out.println("Consultar Grupo de Pesquisa por Disciplina");
		
		DisciplinaView.exibirDisciplina(DisciplinaDAO.selecionarDisciplinas());
		
		System.out.println("Informe o nome da disciplina: ");
		nome = leitura.nextLine();
		
		grupos = GrupoDAO.selecionaGrupoPorDisciplina(nome);
			
		if(grupos.size() > 0) {
			GrupoView.exibirGrupo(grupos);
		}else System.out.println("Não foi encontrado nenhum grupo de pesquisa com a disciplina informada");
			
	}
	
	public static void consultarGrupoMaisUsuarios() {
		
		System.out.println("TOP 10 Grupos com mais usuários");
		
		for(String[] dado:GrupoDAO.consultarGruposMaisUsuarios()) {
			System.out.println("Nome: " + GrupoDAO.selecionarGrupo(Integer.parseInt(dado[0])).getNome() + " Usuários: " + dado[1]);
		}
	
	}
	
	public static void entrarGrupo(UsuarioModel usuarioAtual) {
		
		int codigo = 0;
		
		try {
			
			System.out.println("Informe o código do grupo: ");
			codigo = Integer.parseInt(leitura.nextLine());
			
			if(GrupoDAO.grupoExiste(codigo) || GrupoDAO.usuarioPresente(codigo, usuarioAtual)) {
				throw new GrupoInexistenteException();
			}
			
			GrupoDAO.inserirUsuariosGrupo(codigo, usuarioAtual);
			System.out.println("Usuário entrou no grupo.");
			
		} catch (NumberFormatException excecao) {
			System.out.println("O valor informado não é um número inteiro");
		} catch (GrupoInexistenteException excecao) {
			System.out.println(excecao.getMessage());
		}
			
	}
	
	public static GrupoModel criarGrupo(UsuarioModel usuarioAtual) {
		
		String nome, tipo = null, opcao;
		
		DisciplinaModel disciplina;
		
		System.out.println("Criar Grupo");
		
		System.out.println("Informe o nome do grupo: ");
		nome = leitura.nextLine();
		
		do {
			
			System.out.println("Tipo do grupo\n1. Pesquisa\n2. Trabalho");
			opcao = leitura.nextLine();

			switch (opcao) {
				case "1" -> tipo = "Pesquisa";
				case "2" -> tipo = "Trabalho";
				default -> System.out.println("Opção inválida");
			}
				
		}while(!opcao.equals("1") && !opcao.equals("2"));
		
		disciplina = DisciplinaView.escolherDisciplina(DisciplinaDAO.selecionarDisciplinas());
			
		return new GrupoModel(nome, disciplina, (ProfessorModel) usuarioAtual, tipo);
		
	}
	
	public static void excluirGrupo(UsuarioModel usuarioAtual) {
		
		String opcao;
		
		int codigo = 0;
		
		boolean prosseguir = false;
		
		if(GrupoDAO.selecionaGrupo().size() > 0) {
			
			do {
				
				System.out.println("Excluir Grupo");
				
				exibirGrupo(GrupoDAO.selecionaGrupo());
				
				try {
					
					System.out.println("Informe o nome o código do grupo: ");
					codigo = Integer.parseInt(leitura.nextLine());
					
					if(GrupoDAO.grupoExiste(codigo)) {
						throw new GrupoInexistenteException();
					}
					
					prosseguir = true;
					
				} catch (NumberFormatException excecao) {
					System.out.println("O valor informado não é um número inteiro");
				} catch (GrupoInexistenteException excecao) {
					System.out.println(excecao.getMessage());
				}
				
			}while(!prosseguir);
			
			if(GrupoDAO.selecionarGrupo(codigo).getCriador().equals(usuarioAtual)) {
				
				do {
					
					System.out.println("""
							Você tem certeza que deseja excluir o grupo? Essa ação não pode ser desfeita
							1.Sim
							2.Não""");
					opcao = leitura.nextLine();

					switch (opcao) {
						case "1" -> {
							GrupoDAO.excluirGrupo(codigo);
							System.out.println("Grupo excluído");
						}
						case "2" -> System.out.println("Grupo não excluído");
						default -> System.out.println("Opção invàlida");
					}
				}while(!opcao.equals("1") && !opcao.equals("2"));
				
			} else System.out.println("Ação negada, somente o criador do grupo tem permissão para excluí-lo");

		}else System.out.println("Ação negada, não existem grupos cadastrados.");
		
	}

}
