package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.AlunoDAO;
import dao.ConteudoDAO;
import dao.CursoDAO;
import dao.DisciplinaDAO;
import dao.GrupoDAO;
import dao.ProfessorDAO;
import dao.RelacionamentoDAO;
import model.AlunoModel;
import model.ConteudoModel;
import model.ProfessorModel;
import model.RelacionamentoModel;
import model.UsuarioModel;

public class Principal {

	public static void main(String[] args) {

		Scanner leitura = new Scanner(System.in);

		ArrayList<ConteudoModel> conteudos;
		UsuarioModel usuarioAtual = null, usuarioCadastrar = null, usuarioRel = null;
		boolean comecar = true, sair = true, voltar, entrou = false;
		String opcao, titulo;

		while (comecar) {

			System.out.println("Bem vindo ao IFNET\n1.Entrar\n2.Criar nova conta\nS.Sair");
			opcao = leitura.nextLine().toUpperCase();

			switch (opcao) {

				case "1":
					usuarioAtual = UsuarioView.login();

					if (usuarioAtual != null) {

						entrou = true;

						if (AlunoDAO.eAluno(usuarioAtual.getProntuario())) {
							usuarioAtual = AlunoDAO.selecionarAluno(usuarioAtual.getProntuario());
						} else {
							usuarioAtual = ProfessorDAO.selecionarProfessor(usuarioAtual.getProntuario());
						}
					}
					break;
				case "2":
					
						System.out.println("Você é Aluno ou Professor?\n1 - Aluno\n2 - Professor");
						opcao = leitura.nextLine();

						switch (opcao) {
							case "1" -> {
								
								usuarioCadastrar = UsuarioView.cadastrar();
								
								if(usuarioCadastrar != null) {
									usuarioAtual = AlunoView.cadastrar(usuarioCadastrar);
									AlunoDAO.inserirAluno((AlunoModel) usuarioAtual);
									entrou = true;
								}
							}
							case "2" -> {
								
								usuarioCadastrar = UsuarioView.cadastrar();
								
								if(usuarioCadastrar != null) {
									usuarioAtual = ProfessorView.cadastrar(usuarioCadastrar);
									ProfessorDAO.inserirProfessor((ProfessorModel) usuarioAtual);
									entrou = true;
								}
							}
							default -> System.out.println("Opção inválida");
						}
					break;
				case "S":
					comecar = false;
					break;
				default:
					System.out.println("Opção inválida");
			}

			if (entrou && usuarioAtual != null) {

				do {

					voltar = true;

					System.out.println(usuarioAtual.getNome());
					System.out.println("1.Conteudo\n2.Usuários\n3.Grupo\n4.Conta");
					if (usuarioAtual.getClass() == AlunoModel.class) System.out.println("5.Disciplina\n6.Curso");
					System.out.println("S.Sair");
					opcao = leitura.nextLine().toUpperCase();

					switch (opcao) {

						case "1":

							do {

								System.out.println("1.Pesquisar Conteúdo\n2.Publicar Conteúdo\n3.Excluir Conteúdo\nV.Voltar");
								opcao = leitura.nextLine().toUpperCase();

								switch (opcao) {
									case "1" -> {

										System.out.println("Informe o título do conteúdo: ");
										titulo = leitura.nextLine();
										conteudos = ConteudoDAO.pesquisarConteudos(titulo);

										if (conteudos.size() > 0) {
											ConteudoView.exibirConteudo(conteudos);
										} else {
											System.out.println("Não foi encontrado nenhum conteudo com o título informado");
										}
									}
									case "2" -> ConteudoDAO.insereConteudo(ConteudoView.cadastrarConteudo(usuarioAtual));
									case "3" -> {
										conteudos = ConteudoDAO.selecionaConteudos();
										if (conteudos.size() > 0) {
											ConteudoView.exibirConteudo(conteudos);
										} else {
											System.out.println("Não há nenhum contéudo publicado");
										}
										ConteudoView.excluirConteudo(conteudos, usuarioAtual);
									}
									case "V" -> voltar = false;
									default -> System.out.println("Opção inválida");
								}
							} while (voltar);
							break;
						case "2":

							do {

								System.out.println("""
									1.Relacionar Usuários
									2.Alterar grau de confiabilidade
									3.Excluir Relacionamento
									4.Consultar usuário com mais relacionamentos
									V.Voltar""");
								opcao = leitura.nextLine().toUpperCase();

								switch (opcao) {
									case "1" -> {
										
										usuarioRel = RelacionamentoView.relacionarUsuario(usuarioAtual);
										
										if(usuarioRel != null) {
											RelacionamentoModel.relacionarUsuario(usuarioRel, usuarioAtual);
											RelacionamentoDAO.inserirRelacionamento(usuarioAtual);
											System.out.println("O relacionamento foi criado");
										}
									}
									case "2" -> {
										
										if(usuarioAtual.getRelacionamento().getGrauUsuario().size() != 0) {
											usuarioAtual.setRelacionamento(RelacionamentoDAO.selecionarRelacionamento(usuarioAtual));
											RelacionamentoView.exibirRelacionamentos(usuarioAtual);
											RelacionamentoView.alterarGrauRelacionamento(usuarioAtual);
							
										}else System.out.println("Você não tem nenhum relacionamento.");
										
									}
									case "3" -> {
										
										if(usuarioAtual.getRelacionamento().getGrauUsuario().size() != 0) {
											usuarioAtual.setRelacionamento(RelacionamentoDAO.selecionarRelacionamento(usuarioAtual));
											RelacionamentoView.exibirRelacionamentos(usuarioAtual);
											RelacionamentoView.excluirRelacionamento(usuarioAtual);
							
										}else System.out.println("Você não tem nenhum relacionamento.");
										
									}
									case "4" -> RelacionamentoView.usuarioMaisRelacionados();
									case "V" -> voltar = false;
									default -> System.out.println("Opção inválida");
								}

							} while (voltar);
							break;
						case "3":

							do {

								System.out.println("""
									1.Consultar Grupo de Pesquisa por Disciplina
									2.Consultar Grupos com Mais Usuarios
									3.Entrar no Grupo""");
								if (usuarioAtual.getClass() == ProfessorModel.class)
									System.out.println("4.Criar Grupo\n5.Excluir Grupo");
								System.out.println("V.Volta");
								opcao = leitura.nextLine().toUpperCase();

								switch (opcao) {
									case "1" -> {
										
										if(GrupoDAO.selecionaGrupo().size() > 0) {
											GrupoView.consultarGrupoPesqPorDisciplina();
										} else System.out.println("Nenhum grupo foi criado.");
									} 
									
									case "2" -> {
										
										if(GrupoDAO.selecionaGrupo().size() > 0) {
											GrupoView.consultarGrupoMaisUsuarios();
										} else System.out.println("Nenhum grupo foi criado.");
									}
									
									case "3" -> {
										
										if(GrupoDAO.selecionaGrupo().size() > 0) {
											GrupoView.exibirGrupo(GrupoDAO.selecionaGrupo());
											GrupoView.entrarGrupo(usuarioAtual);
										} else System.out.println("Nenhum grupo foi criado.");
									}
									
								}

								if (usuarioAtual.getClass() == ProfessorModel.class) {
									switch (opcao) {
										case "4" -> {
											GrupoDAO.inserirGrupo(GrupoView.criarGrupo(usuarioAtual));
											System.out.println("Grupo criado!");
										}
										case "5" -> {
											
											if(GrupoDAO.selecionaGrupo().size() > 0) {
												GrupoView.excluirGrupo(usuarioAtual);
											}else System.out.println("Ação negada, não existem grupos cadastrados.");
										}
									}
								}

								if (usuarioAtual.getClass() == ProfessorModel.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4") && !opcao.equals("5")) && !opcao.equals("6") ||
										usuarioAtual.getClass() == AlunoModel.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4"))) {

									if ("V".equals(opcao)) voltar = false;
									else System.out.println("Opção inválida");
								}

							} while (voltar);
							break;
						case "4":

							do {

								System.out.println("Conta");
								System.out.println("1.Mudar Nome\n2.Mudar Senha\n3.Excluir Conta\nV.voltar");
								opcao = leitura.nextLine().toUpperCase();

								switch (opcao) {
									case "1" -> UsuarioView.mudarNome(usuarioAtual);
									case "2" -> UsuarioView.mudarSenha(usuarioAtual);
									case "3" -> {
										if (UsuarioView.excluirConta(usuarioAtual)) {
											sair = entrou = voltar = false;
										}
									}
									case "V" -> voltar = false;
									default -> System.out.println("Opção inválida");
								}
							} while (voltar);
							break;
					}

					if (usuarioAtual.getClass() == AlunoModel.class) {

						switch (opcao) {

							case "5":

								do {
									System.out.println("1.Cadastrar Disciplina\n2.Excluir Disciplina\nV.Voltar");
									opcao = leitura.nextLine().toUpperCase();

									switch (opcao) {
										case "1" -> DisciplinaDAO.insereDisciplina(DisciplinaView.cadastrarDisciplina());
										case "2" -> {
											if(DisciplinaDAO.selecionarDisciplinas().size() > 0) {
												DisciplinaView.excluirDisciplina(DisciplinaDAO.selecionarDisciplinas());
											}else System.out.println("Ação negada, não existem disciplinas cadastrados.");
										}
										case "V" -> voltar = false;
										default -> System.out.println("Opção inválida");
									}
								} while (voltar);
								break;
							case "6":

								do {
									System.out.println("1.Cadastrar Curso\n2.Excluir Curso\nV.Voltar");
									opcao = leitura.nextLine().toUpperCase();

									switch (opcao) {
										case "1" -> {
											CursoDAO.inserirCurso(CursoView.cadastrarCurso());
											System.out.println("Curso cadastrado");
										}
										case "2" -> {
											if(CursoDAO.selecionarCursos().size() > 0) {
												CursoView.excluirCurso();
											}else System.out.println("Ação negada, não existem cursos cadastrados.");
											}
										case "V" -> voltar = false;
										default -> System.out.println("Opção inválida");
									}
								} while (voltar);
								break;
						}
					}

					if (usuarioAtual.getClass() == AlunoModel.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4") && !opcao.equals("5") && !opcao.equals("6") && !opcao.equals("V")) ||
							usuarioAtual.getClass() == ProfessorModel.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4")) && !opcao.equals("V")) {
						if ("S".equals(opcao)) {
							sair = entrou = false;
							usuarioAtual = null;
						} else {
							System.out.println("Opção inválida");
						}
					}
				}while(sair);
			}
		}
	}
}