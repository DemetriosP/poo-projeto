package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dao.AlunoDAO;
import dao.ConteudoDAO;
import dao.CursoDAO;
import dao.DisciplinaDAO;
import dao.GrupoDAO;
import dao.ProfessorDAO;
import dao.RelacionamentoDAO;
import dao.UsuarioDAO;
import excecoes.GrauConfiabilidadeAtualException;
import excecoes.OpcaoInexistenteException;
import excecoes.UsuarioCadastradoException;
import model.AlunoModel;
import model.AreaModel;
import model.ConteudoModel;
import model.CursoModel;
import model.DisciplinaModel;
import model.GrupoModel;
import model.ProfessorModel;
import model.RelacionamentoModel;
import model.UsuarioModel;

public class Principal {

	public static void main(String[] args) {
		
		Scanner leitura = new Scanner(System.in);
		
		ArrayList<ConteudoModel> conteudos;
		ArrayList<String[]> dados = new ArrayList<String[]>();
		UsuarioModel usuario;
		UsuarioModel usuarioAtual = null;
		boolean comecar = true, sair = true, voltar = true, entrou = false, cadastro = false;
		String opcao = "", titulo;
		
		while(comecar) {
			
			do {
				
				System.out.println("Bem vindo ao IFNET");
				System.out.println("1.Entrar\n2.Criar nova conta\nS.Sair");
				opcao = leitura.nextLine().toUpperCase();
				
				switch(opcao) {
				
					case "1":
						
						do {
							
							usuarioAtual = UsuarioView.login();
							
							if(usuario != null) {
								
								if(AlunoDAO.eAluno(usuarioAtual.getProntuario())) {
									usuarioAtual = AlunoDAO.selecionarAluno(usuarioAtual.getProntuario());
									entrou = true;
								}else {
									usuarioAtual = ProfessorDAO.selecionarProfessor(usuarioAtual.getProntuario());
									entrou = true;
								}
							}
							
						}while(!entrou);
						
						break;
					case "2":
						
						do {
							
							System.out.println("Você é Aluno ou Professor?\n1 - Aluno\n2 - Professor");
							opcao = leitura.nextLine();
					
							switch(opcao) {
							
								case "1":
									usuario = new AlunoView().cadastrar();
									if(usuario != null) AlunoDAO.inserirAluno((AlunoModel) usuario);
									cadastro = true;
									break;
								case "2":
									usuario = new ProfessorView().cadastrar();
									if(usuario != null) ProfessorDAO.inserirProfessor((ProfessorModel) usuario);
									cadastro = true;
									break;
								default:
									System.out.println("Opção inválida");
							}
					
						}while(!cadastro);
								
						break;
					case "S":
						comecar = sair = false;
						break;
					default:
						System.out.println("Opção inválida");
				
			}while(sair);
			
			sair = true;
			
			if(entrou) {
				
				do {
						
					voltar = true;
					
					System.out.println(usuarioAtual.getNome());
					System.out.println("1.Conteudo\n2.Usuários\n3.Grupo\n4.Conta");
					if(usuarioAtual.getClass() == AlunoModel.class) System.out.println("5.Disciplina\n6.Curso");
					System.out.println("S.Sair");
					opcao = leitura.nextLine().toUpperCase();
				
					switch(opcao) {
					
						case "1":
							
							do {
								
								System.out.println("1.Pesquisar Conteúdo\n2.Publicar Conteúdo\n3.Excluir Conteúdo\nV.Voltar");
								opcao = leitura.nextLine().toUpperCase();
								
								switch(opcao) {
								
									case "1":
										
										System.out.println("Informe o título do conteúdo: ");
										titulo = leitura.nextLine();
										
										conteudos = ConteudoDAO.pesquisarConteudos(titulo);
										
										if(conteudos.size() > 0) {
											ConteudoView.exibirConteudo(conteudos);
										}else {
											System.out.println("Não foi encontrado nenhum conteudo com o título informado");
										}
										
										break;
									case "2":
										
										ConteudoDAO.insereConteudo(ConteudoView.cadastrarConteudo(usuarioAtual));
										
										break;
									case "3":
										
										conteudos = ConteudoDAO.selecionaConteudos();
										
										if(conteudos.size() > 0) {
											ConteudoView.exibirConteudo(conteudos);
										}else {
											System.out.println("Não há nenhum contéudo publicado");
										}
										
										ConteudoView.excluirConteudo(conteudos, usuarioAtual);
							
										break;
									case "V":
										voltar = false;
										break;
									default:
										System.out.println("Opção inválida");
								}
							}while(voltar);
						
							break;
						case "2":
							
							do {
								
								System.out.println("1.Relacionar Usuários\n2.Alterar grau de confiabilidade\n3.Excluir Relacionamento"
										+ "\n4.Consultar usuário com mais relacionamentos\nV.Voltar");
								opcao = leitura.nextLine().toUpperCase();
								
								switch(opcao) {
									
									case "1":
										
										RelacionamentoView.relacionarUsuario(usuarioAtual);
										break;
									case "2":

										RelacionamentoView.alterarGrauRelacionamento(usuarioAtual);
										break;
									case "3":
										
										RelacionamentoView.excluirRelacionamento(usuarioAtual);
										break;
									case "4":
										
										RelacionamentoView.usuarioMaisRelacionados();
										break;
									case "V":
										voltar = false;
										break;
									default:
										System.out.println("Opção inválida");
								}
								
							}while(voltar);
							
							break;
						case "3":
							
							do {
								
								System.out.println("1.Consultar Grupo de Pesquisa por Disciplina\n"
										+ "2.Consultar Grupos com Mais Usuarios\n3.Entrar no Grupo");
								if(usuarioAtual.getClass() == ProfessorModel.class) 
									System.out.println("4.Criar Grupo\n5.Excluir Grupo");
								System.out.println("V.Volta");
								opcao = leitura.nextLine().toUpperCase();
					
								switch(opcao) {
								
									case "1":
										GrupoView.consultarGrupoPesqPorDisciplina();
										break;
									case "2":
										GrupoView.consultarGrupoMaisUsuarios();
										break;
									case "3":
										GrupoView.entrarGrupo(usuarioAtual);
										break;	
								}
								
								if(usuarioAtual.getClass() == ProfessorModel.class) {
									switch(opcao) {
										case "4":
											GrupoDAO.inserirGrupo(GrupoView.criarGrupo(usuarioAtual));
											System.out.println("Grupo criado!");
											break;
										case "5":
											GrupoView.excluirGrupo(usuarioAtual);
											break;
									}	
								}
								
								if(usuarioAtual.getClass() == ProfessorModel.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4") && !opcao.equals("5")) && !opcao.equals("6")|| 
										usuarioAtual.getClass() == AlunoModel.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4"))) {
									switch(opcao) {
									case "V":
										voltar = false;
										break;
									default:
										System.out.println("Opção inválida");
									}
								}
								
							}while(voltar);
							
							break;
						
						case "4":
							
							do {
								
								System.out.println("Conta");
								System.out.println("1.Mudar Nome\n2.Mudar Senha\n3.Excluir Conta\nV.voltar");
								opcao = leitura.nextLine().toUpperCase();
								
								switch(opcao) {
									case "1":
										UsuarioView.mudarNome(usuarioAtual);
										break;
									case "2":
										UsuarioView.mudarSenha(usuarioAtual);
										break;
									case "3":
										
										if(UsuarioView.excluirConta(usuarioAtual)) {
											sair = false;
											entrou = false;
											voltar = false;
										}
										break;
									case "V":
										voltar = false;
										break;
									default:
										System.out.println("Opção inválida");
								}
					
							}while(voltar);
							break;	
					}	
					
					if(usuarioAtual.getClass() == AlunoModel.class) {
						
						switch(opcao) {
							
						case "5":
							
							do {
								
								System.out.println("1.Cadastrar Disciplina\n2.Excluir Disciplina\nV.Voltar");
								opcao = leitura.nextLine().toUpperCase();
								
								switch(opcao) {
								
									case "1":									
										DisciplinaView.cadastrarDisciplina();
										break;
									case "2":
										DisciplinaView.excluirDisciplina(DisciplinaDAO.selecionarDisciplinas());
										break;
									case "V":
										voltar = false;
										break;
									default:
										System.out.println("Opção inválida");
								}
								
							}while(voltar);
							
							break;
						
						case "6":
							
							do {
								
								System.out.println("1.Cadastrar Curso\n2.Excluir Curso\nV.Voltar");
								opcao = leitura.nextLine().toUpperCase();
								
								switch(opcao) {
							
 									case "1":
										CursoDAO.inserirCurso(CursoView.cadastrarCurso());
 										System.out.println("Curso cadastrado");
										break;
									case "2":
										CursoView.excluirCurso();
										break;
									case "V":
										voltar = false;
										break;
									default:
										System.out.println("Opção inválida");
								}
								
							}while(voltar);
							
							break;
						}
					}
					
					if(usuarioAtual.getClass() == AlunoModel.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4") && !opcao.equals("5") && !opcao.equals("6") && !opcao.equals("V")) || 
							usuarioAtual.getClass() == ProfessorModel.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4")) && !opcao.equals("V")){
						switch (opcao){
						case "S":
							sair = false;
							entrou = false;
							break;
						default:
							System.out.println("Opção inválida");
						}
					}
					
				}while(sair);	
			}
		}			
	}				
}					
		