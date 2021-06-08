package view;

import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.cj.ParseInfo;

import dao.DisciplinaDAO;
import dao.GrupoDAO;
import excecoes.GrupoInexistenteException;
import excecoes.OpcaoInexistenteException;
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
		
		System.out.println("Disciplinas");
		
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
			System.out.println(GrupoDAO.selecionarGrupo(Integer.parseInt(dado[0])) + "Usuários - " + dado[1]);
		}
	
	}
	
	public static void entrarGrupo(UsuarioModel usuarioAtual) {
		
		int codigo = 0;
		
		boolean prosseguir = false;
		
		do {
			
			exibirGrupo(GrupoDAO.selecionaGrupo());
			
			try {
				
				System.out.println("Informe o nome o código do grupo: ");
				codigo = Integer.parseInt(leitura.nextLine());
				
				if(!GrupoDAO.grupoExiste(codigo)) {
					throw new GrupoInexistenteException();
				}
				
				prosseguir = true;
				
			} catch (NumberFormatException excecao) {
				System.out.println("O valor informado não é um número inteiro");
			} catch (GrupoInexistenteException excecao) {
				System.out.println(excecao.getMessage());
			}
			
		}while(!prosseguir);
		
		try {
			
			if(GrupoDAO.usuarioPresente(codigo, usuarioAtual)) {
				throw new GrupoInexistenteException();
			}
			
			GrupoDAO.inserirUsuariosGrupo(codigo, usuarioAtual);
			System.out.println("Usuário entrou no grupo.");
			
		}catch (GrupoInexistenteException excecao) {
			System.out.println(excecao.getMessage());
		}
			
	}
	
	public static GrupoModel criarGrupo(UsuarioModel usuarioAtual) {
		
		String nome, tipo;
		
		DisciplinaModel disciplina;
		
		System.out.println("Criar Grupo");
		
		System.out.println("Informe o nome do grupo: ");
		nome = leitura.nextLine();
		
		do {
			
			System.out.println("Tipo do grupo\n1. Pesquisa\n2. Trabalho");
			tipo = leitura.nextLine();
			
			switch (tipo) {
				case "1":
					tipo = "Pesquisa";
					break;
				case "2":
					tipo = "Trabalho";
					break;
				default:
					System.out.println("Opção inválida");
			}
				
		}while(!tipo.equals("1") && !tipo.equals("2"));
		
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
					
					if(!GrupoDAO.grupoExiste(codigo)) {
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
					
					System.out.println("Você tem certeza que deseja excluir o grupo? "
							+ "Essa ação não pode ser desfeita\n1.Sim\n2.Não");
					opcao = leitura.nextLine();
					
					switch(opcao) {
					
						case "1":
							GrupoDAO.excluirGrupo(codigo);
							System.out.println("Grupo excluído");
							break;
						case "2":
							System.out.println("Grupo não excluído");
							break;
						default:
							System.out.println("Opção invàlida");
					}
				}while(!opcao.equals("1") && !opcao.equals("2"));
				
			} else System.out.println("Ação negada, somente o criador do grupo tem permissão para excluí-lo");

		}else System.out.println("Ação negada, não existem grupos cadastrados.");
		
	}

}
