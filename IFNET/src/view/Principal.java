package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dao.AlunoDAO;
import dao.ConteudoDAO;
import dao.ProfessorDAO;
import dao.RelacionamentoDAO;
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
		
		ArrayList<AreaModel> areas = new ArrayList<AreaModel>();
		ArrayList<DisciplinaModel> disciplinas = new ArrayList<DisciplinaModel>();
		ArrayList<CursoModel> cursos = new ArrayList<CursoModel>();
		ArrayList<UsuarioModel> usuarios = new ArrayList<UsuarioModel>();
		ArrayList<ConteudoModel> conteudos = new ArrayList<ConteudoModel>();
		ArrayList<GrupoModel> grupos = new ArrayList<GrupoModel>();
		ArrayList<GrupoModel> grupoAlt = new ArrayList<GrupoModel>();
		ArrayList<ConteudoModel> conteudoAlt = new ArrayList<ConteudoModel>();
		ArrayList<DisciplinaModel> disciplinaAlt = new ArrayList<DisciplinaModel>();
		ArrayList<UsuarioModel> usuariosAlt = new ArrayList<UsuarioModel>();
		ArrayList<UsuarioModel> usuariosMapa = new ArrayList<UsuarioModel>();
		ArrayList<CursoModel> cursoAlt = new ArrayList<CursoModel>();
		Map<Integer,Integer> maisRelacionados = new HashMap<Integer, Integer>();
		Map<Integer,Integer> maisUsuarios = new HashMap<Integer, Integer>();
		
		ArrayList<String[]> dados = new ArrayList<String[]>();
		
		Scanner leitura = new Scanner(System.in);
		
		UsuarioModel usu = null;
		CursoModel cur = null;
		DisciplinaModel disc = null;
		GrupoModel grup = null;
		ConteudoModel cont = null;
		DisciplinaModel disciplinaCadastro;
		CursoModel cursoCadastro;
		AreaModel areaCadastro;
		UsuarioModel usuarioAtual = null;
		boolean comecar = true, sair = true, voltar = true, entrou = false, prosseguir = false, cadastro = false, relacionar = false;
		String opcao = "", prontuario = "", senha, nome, tipo, titulo;	
		int posicao = -1, semestre = 0, semestres = 0, grau, grauAtual = -1, novoGrau = -1, volta;
		
		UsuarioModel usuario;
		
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
								
								if(grupos.size() > 0) {
									
									do {
										
										if(grupoAlt.size() == 0) {
											grupoAlt = new ArrayList<GrupoModel>(grupos);
											System.out.println("Grupos");
										}else System.out.println("Resultado da busca");
										
										for(GrupoModel grupo:grupoAlt) {
											System.out.println(grupoAlt.indexOf(grupo) +". " + grupo.getNome());
										}
										
										System.out.println("Informe o número do conteúdo desejado: ");
										
										try {
											
											posicao = Integer.parseInt(leitura.nextLine());
											
											if(posicao >= grupoAlt.size() || posicao < 0) {
												throw new OpcaoInexistenteException();
											}
											
											prosseguir = true;
											grup = grupoAlt.get(posicao);
											
										} catch (NumberFormatException excecao) {
											System.out.println("O valor informado não é um número inteiro");
										} catch (OpcaoInexistenteException excecao) {
											System.out.println(excecao.getMessage());
											grupoAlt.clear();
										}
										
									}while(!prosseguir);
									
									System.out.println(grupoAlt.get(posicao));
							
								}
								
								System.out.println("1.Pesquisar Grupo\n2.Consultar Grupo de Pesquisa por Disciplina\n"
										+ "3.Consultar Grupos com Mais Usuarios\n4.Entrar no Grupo");
								if(usuarioAtual.getClass() == ProfessorModel.class) 
									System.out.println("5.Criar Grupo\n6.Excluir Grupo");
								System.out.println("V.Volta");
								opcao = leitura.nextLine().toUpperCase();
								
								grupoAlt.clear();
								prosseguir = false;
								
								switch(opcao) {
								
									case "1":
										
										System.out.println("Pesquisar Grupo");
										
										System.out.println("Informe o nome do grupo: ");
										nome = leitura.nextLine();
										
										grupoAlt = GrupoModel.pesquisarGrupos(grupos, nome);
										
										if(grupoAlt.size() == 0) System.out.println("Não foi encontrado nenhum grupo com o nome informado");
										
										break;
									case "2":
										
										do {
											
											System.out.println("Consultar Grupo de Pesquisa por Disciplina");
											
											System.out.println("Disciplinas");
											
											for(DisciplinaModel disciplina:disciplinas) {
												posicao = disciplinas.indexOf(disciplina);
												System.out.println(posicao + ". " + disciplina.getNome());
											}
											
											System.out.println("Informe o número da disciplina: ");
											
											try {
												
												posicao = Integer.parseInt(leitura.nextLine());
												
												if(posicao >= disciplinas.size() || posicao < 0) {
													throw new OpcaoInexistenteException();
												}
												
												prosseguir = true;
												
											} catch(NumberFormatException excecoes) {
												System.out.println("O valor informado não é um número inteiro");
											} catch(OpcaoInexistenteException excecoes) {
												System.out.println(excecoes.getMessage());
												grupoAlt.clear();
											}
										
										}while(!prosseguir);
										
										grupoAlt = GrupoModel.consultarGpPesquisaPorDisciplina(grupos, disciplinas.get(posicao));
										
										if(grupoAlt.size() == 0) System.out.println("Não foi encontrado nenhum grupo de pesquisa com a disciplina informada");
									
										prosseguir = false;

										break;
										
									case "3":
										
										maisUsuarios = GrupoModel.consultarGrupoMaisUsuarios(grupos);
										
										System.out.println("TOP 10 Grupos com mais usuários");
										
										volta = 0;
										
										for (Map.Entry<Integer , Integer> mapa : maisUsuarios.entrySet()) {
											System.out.println("Grupo: " + grupos.get(mapa.getKey()).getNome() + "\nQuantidade de usuários: " + mapa.getValue() + "\n");
											volta++;
											if(volta == 9) break;
										}
										
										break;
									case "4":
										
										posicao = grupos.indexOf(grup);
										
										if(!grupos.get(posicao).getUsuariosGrupo().contains(usuarioAtual)) {
											grupos.get(posicao).setUsuarioGrupo(usuarioAtual);
											System.out.println("Usuário foi aceito no grupo");
										}else System.out.println("O usuário já faz parte do grupo");
											
								}
								
								if(usuarioAtual.getClass() == ProfessorModel.class) {
									switch(opcao) {
										case "5":
											
											System.out.println("Criar Grupo");
											
											System.out.println("Informe o nome do grupo: ");
											nome = leitura.nextLine();
											
											do {
												
												System.out.println("Tipo do grupo\n1. Pesquisa\n2. Trabalho");
												tipo = leitura.nextLine();
												
												switch (opcao) {
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
											
											do {
												
												System.out.println("Disciplina");
												
												for(DisciplinaModel disciplina:disciplinas) {
													posicao = disciplinas.indexOf(disciplina);
													System.out.println(posicao + ". " + disciplina.getNome());
												}
												
												System.out.println("Informe o número da disciplina desejada: ");
												
												try {
													
													posicao = Integer.parseInt(leitura.nextLine());
													
													if(posicao >= conteudos.size() || posicao < 0) {
														throw new OpcaoInexistenteException();
													}
													
													prosseguir = true;
													
												} catch (NumberFormatException excecao) {
													System.out.println("O valor informado não é um número inteiro");
												} catch (OpcaoInexistenteException excecao) {
													System.out.println(excecao.getMessage());
												}
												
											}while(!prosseguir);
												
											grupos.add(new GrupoModel(nome, disciplinas.get(posicao), (ProfessorModel) usuarioAtual, tipo));
											
											System.out.println("Grupo criado!");
											
											prosseguir = false;
											
											break;
										case "6":
											
											if(grupos.size() > 0) {
												
												if(grup.getCriador().equals(usuarioAtual)) {
													
													do {
														
														System.out.println("Você tem certeza que deseja excluir o grupo? "
																+ "Essa ação não pode ser desfeita\n1.Sim\n2.Não");
														opcao = leitura.nextLine();
														
														switch(opcao) {
														
															case "1":
																grupos.remove(grup);
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
										System.out.println("Nome Atual: " + usuarioAtual.getNome());
										System.out.print("Novo nome: ");
										nome = leitura.nextLine();
										usuarioAtual.setNome(nome);
										System.out.println("Nome alterado");
										break;
									case "2":
										System.out.println("Senha Atual: " + usuarioAtual.getSenha());
										System.out.print("Novo senha: ");
										senha = leitura.nextLine();
										usuarioAtual.setSenha(senha);
										System.out.println("Senha alterado");
										break;
									case "3":
										
										boolean excluido = false;
										
										do {
											
											System.out.println("Você tem certeza que deseja excluir a sua conta? "
													+ "Essa ação não pode ser desfeita\n1.Sim\n2.Não");
											opcao = leitura.nextLine();
											
											switch(opcao) {
											
												case "1":
													usuarios.remove(usuarioAtual);
													excluido = true;
													System.out.println("Conta excluída");
													break;
												case "2":
													excluido = false;
													System.out.println("Conta não excluída");
													break;
												default:
													System.out.println("Opção invàlida");
											}
											
										}while(!opcao.equals("1") && !opcao.equals("2"));
										
										if(excluido) {
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
								
								if(disciplinas.size() > 0) {
									
									do {
										
										if(disciplinaAlt.size() == 0) {
											disciplinaAlt = new ArrayList<DisciplinaModel>(disciplinas);
											System.out.println("Disciplinas");
										}else System.out.println("Resultado da busca");
										
										for(DisciplinaModel disciplina:disciplinaAlt) {
											System.out.println(disciplinaAlt.indexOf(disciplina) +". " + disciplina.getNome());
										}
										
										System.out.println("Informe o número da disciplina desejado: ");
										
										try {
											
											posicao = Integer.parseInt(leitura.nextLine());
											
											if(posicao >= disciplinaAlt.size() || posicao < 0) {
												throw new OpcaoInexistenteException();
											}
											
											prosseguir = true;
											disc = disciplinaAlt.get(posicao);
											
										} catch (NumberFormatException excecao) {
											System.out.println("O valor informado não é um número inteiro");
										} catch (OpcaoInexistenteException excecao) {
											System.out.println(excecao.getMessage());
											disciplinaAlt.clear();
										}
										
									}while(!prosseguir);
									
									System.out.println("Nome: " + disciplinaAlt.get(posicao).getNome());
									
								}
								
								System.out.println("1.Pesquisar Disciplina\n2.Cadastrar Disciplina\n3.Excluir Disciplina\nV.Voltar");
								opcao = leitura.nextLine().toUpperCase();
								
								disciplinaAlt.clear();
								prosseguir = false;
								
								switch(opcao) {
								
									case "1":
										
										System.out.println("Informe o nome da disciplina: ");
										nome = leitura.nextLine();
										
										disciplinaAlt = DisciplinaModel.pesquisarDisciplinas(disciplinas, nome);
										
										if(disciplinaAlt.size() == 0) System.out.println("Não foi encontrado nenhuma disciplina com o nome informado");
								
										break;
									case "2":
										
											System.out.println("Informe O nome da Diciplina que deseja adicionar");
											nome = leitura.nextLine();
											
											disciplinas.add(new DisciplinaModel(nome));
											
											System.out.println("Disciplina cadastrada");
											
										break;
									case "3":
				
										if(disciplinas.size() > 0) {
											
											do {
												System.out.println("Você tem certeza que deseja excluir a disciplina? "
														+ "Essa ação não pode ser desfeita\n1.Sim\n2.Não");
												opcao = leitura.nextLine();
												
												switch(opcao) {
												
													case "1":
														disciplinas.remove(disc);
														System.out.println("Disciplina excluído");
														break;
													case "2":
														System.out.println("Disciplina não excluído");
														break;
													default:
														System.out.println("Opção invàlida");
												}
											}while(!opcao.equals("1") && !opcao.equals("2"));
												
										}else System.out.println("Ação negada, não existem disciplinas cadastrados.");
											
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
								
								if(cursos.size() > 0) {
									
									do {
										
										if(cursoAlt.size() == 0) {
											cursoAlt = new ArrayList<CursoModel>(cursos);
											System.out.println("Curso");
										}else System.out.println("Resultado da busca");
										
										for(CursoModel curso:cursoAlt) {
											System.out.println(cursoAlt.indexOf(curso) +". " + curso.getNome());
										}
										
										System.out.println("Informe o número do curso desejado: ");
										
										try {
											
											posicao = Integer.parseInt(leitura.nextLine());
											
											if(posicao >= cursoAlt.size() || posicao < 0) {
												throw new OpcaoInexistenteException();
											}
											
											prosseguir = true;
											cur = cursoAlt.get(posicao);
											
										} catch (NumberFormatException excecao) {
											System.out.println("O valor informado não é um número inteiro");
										} catch (OpcaoInexistenteException excecao) {
											System.out.println(excecao.getMessage());
											cursoAlt.clear();
										}
										
									}while(!prosseguir);
									
									System.out.println(cursoAlt.get(posicao));
								}
								
								System.out.println("1.Pesquisar Curso\n2.Cadastrar Curso\n3.Excluir Curso\nV.Voltar");
								opcao = leitura.nextLine().toUpperCase();
								
								cursoAlt.clear();
								prosseguir = false;
								
								switch(opcao) {
								
									case "1":
										
										System.out.println("Informe o nome do curso: ");
										nome = leitura.nextLine();
										
										cursoAlt = CursoModel.pesquisaCurso(cursos, nome);
										
										if(cursoAlt.size() == 0) System.out.println("Não foi encontrado nenhum curso com o nome informado");
										break;
 									case "2":
										
										System.out.println("Cadastrar Curso");
										
										System.out.println("Informe o nome do curso: ");
										nome = leitura.nextLine();

										do{

											try{

											System.out.println("Informe a quantidade de semestres do curso: ");
											semestres = Integer.parseInt(leitura.nextLine());

											prosseguir = true;

											}catch(NumberFormatException excecoes) {
												System.out.println("O valor informado não é um número inteiro");
											}

										}while(!prosseguir);

										prosseguir = false;
										
										cur = new CursoModel(nome, semestres);
										
										do {
											
											do {
											
												System.out.println("Disciplina");
												
												for(DisciplinaModel disciplina:disciplinas) {
													posicao = disciplinas.indexOf(disciplina);
													System.out.println(posicao + ". " + disciplina.getNome());
												}
												
												System.out.println("Informe o número da disciplina desejada: ");
												
												try {
													posicao = Integer.parseInt(leitura.nextLine());
													
													if(posicao >= disciplinas.size() || posicao < 0) {
														throw new OpcaoInexistenteException();
													}
													
													prosseguir = true;
													
												}catch(NumberFormatException excecoes) {
													System.out.println("O valor informado não é um número inteiro");
												}catch (OpcaoInexistenteException excecao) {
													System.out.println(excecao.getMessage());
												}
											
											}while(!prosseguir);

											prosseguir = false;

											do{

												try{

												System.out.println("Informe o semetre da disciplina");
												semestre = Integer.parseInt(leitura.nextLine());

												if(semestre > semestres || semestre  < 0) {
														throw new OpcaoInexistenteException();
													}

												prosseguir = true;

												}catch(NumberFormatException excecoes) {
													System.out.println("O valor informado não é um número inteiro");

												}catch (OpcaoInexistenteException excecao) {
													System.out.println(excecao.getMessage());
												}

											}while(!prosseguir);

											prosseguir = false;

											cur.setDisciplinasPorSemestre(semestre, disciplinas.get(posicao));
											
											do {
												System.out.println("Deseja cadastrar outra disciplina no curso?\n1. Sim\n2. Não");
												opcao = leitura.nextLine();
												
												if(!opcao.equals("1") && !opcao.equals("2")) System.out.println("Opção inválida");
													
											}while(!opcao.equals("1") && !opcao.equals("2"));
																						
										}while(opcao.equals("1"));
										
										cursos.add(cur);
										
										System.out.println("Curso cadastrado");
				
										break;
									case "3":
										
										if(cursos.size() > 0) {
											
											do {
												
												System.out.println("Você tem certeza que deseja excluir o curso? "
														+ "Essa ação não pode ser desfeita\n1.Sim\n2.Não");
												opcao = leitura.nextLine();
												
												switch(opcao) {
												
													case "1":
														cursos.remove(cur);
														System.out.println("Curso excluído");
														break;
													case "2":
														System.out.println("Curso não excluído");
														break;
													default:
														System.out.println("Opção invàlida");
												}
											}while(!opcao.equals("1") && !opcao.equals("2"));

										}else System.out.println("Ação negada, não existem cursos cadastrados.");
										
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
		