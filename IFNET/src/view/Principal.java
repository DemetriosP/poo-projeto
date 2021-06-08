package view;

import java.util.ArrayList;
import java.util.Scanner;

import dao.AlunoDAO;
import dao.ConteudoDAO;
import dao.CursoDAO;
import dao.DisciplinaDAO;
import dao.GrupoDAO;
import dao.ProfessorDAO;
import model.AlunoModel;
import model.ConteudoModel;
import model.ProfessorModel;
import model.UsuarioModel;

public class Principal {

	public static void main(String[] args) {

		Scanner leitura = new Scanner(System.in);

		ArrayList<ConteudoModel> conteudos;
		UsuarioModel usuarioAtual = null;
		boolean comecar = true, sair = true, voltar, entrou = false, cadastro = false;
		String opcao, titulo;

		while (comecar) {

			do {

				System.out.println("Bem vindo ao IFNET\n1.Entrar\n2.Criar nova conta\nS.Sair");
				opcao = leitura.nextLine().toUpperCase();

				switch (opcao) {

					case "1":
						do {

							usuarioAtual = UsuarioView.login();

							if (usuarioAtual != null) {

								entrou = true;

								if (AlunoDAO.eAluno(usuarioAtual.getProntuario())) {
									usuarioAtual = AlunoDAO.selecionarAluno(usuarioAtual.getProntuario());
								} else {
									usuarioAtual = ProfessorDAO.selecionarProfessor(usuarioAtual.getProntuario());
								}
							}

						} while (!entrou);
						break;
					case "2":
						do {

							System.out.println("Você é Aluno ou Professor?\n1 - Aluno\n2 - Professor");
							opcao = leitura.nextLine();

							switch (opcao) {
								case "1" -> {
									usuarioAtual = new AlunoView().cadastrar();
									if (usuarioAtual != null) AlunoDAO.inserirAluno((AlunoModel) usuarioAtual);
									cadastro = true;
								}
								case "2" -> {
									usuarioAtual = new ProfessorView().cadastrar();
									if (usuarioAtual != null) ProfessorDAO.inserirProfessor((ProfessorModel) usuarioAtual);
									cadastro = true;
								}
								default -> System.out.println("Opção inválida");
							}

						} while (!cadastro);
						break;
					case "S":
						comecar = sair = false;
						break;
					default:
						System.out.println("Opção inválida");
				}

				if (entrou && usuarioAtual != null) {

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
									case "1" -> RelacionamentoView.relacionarUsuario(usuarioAtual);
									case "2" -> RelacionamentoView.alterarGrauRelacionamento(usuarioAtual);
									case "3" -> RelacionamentoView.excluirRelacionamento(usuarioAtual);
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
									case "1" -> GrupoView.consultarGrupoPesqPorDisciplina();
									case "2" -> GrupoView.consultarGrupoMaisUsuarios();
									case "3" -> GrupoView.entrarGrupo(usuarioAtual);
								}

								if (usuarioAtual.getClass() == ProfessorModel.class) {
									switch (opcao) {
										case "4" -> {
											GrupoDAO.inserirGrupo(GrupoView.criarGrupo(usuarioAtual));
											System.out.println("Grupo criado!");
										}
										case "5" -> GrupoView.excluirGrupo(usuarioAtual);
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
										case "1" -> DisciplinaView.cadastrarDisciplina();
										case "2" -> DisciplinaView.excluirDisciplina(DisciplinaDAO.selecionarDisciplinas());
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
										case "2" -> CursoView.excluirCurso();
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
							sair = false;
							entrou = false;
							usuarioAtual = null;
						} else {
							System.out.println("Opção inválida");
						}
					}

				}

			} while (sair);
		}
	}

}


