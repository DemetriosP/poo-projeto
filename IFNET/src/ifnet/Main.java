package ifnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import excecoes.GrauConfiabilidadeAtualException;
import excecoes.OpcaoInexistenteException;
import excecoes.UsuarioCadastradoException;

public class Main {

	public static void main(String[] args) {
		
		
		ArrayList<Area> areas = new ArrayList<Area>();
		ArrayList<Disciplina> disciplinas = new ArrayList<Disciplina>();
		ArrayList<Curso> cursos = new ArrayList<Curso>();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		ArrayList<Conteudo> conteudos = new ArrayList<Conteudo>();
		ArrayList<Grupo> grupos = new ArrayList<Grupo>();
		ArrayList<Grupo> grupoAlt = new ArrayList<Grupo>();
		ArrayList<Conteudo> conteudoAlt = new ArrayList<Conteudo>();
		ArrayList<Disciplina> disciplinaAlt = new ArrayList<Disciplina>();
		ArrayList<Usuario> usuariosAlt = new ArrayList<Usuario>();
		ArrayList<Usuario> usuariosMapa = new ArrayList<Usuario>();
		ArrayList<Curso> cursoAlt = new ArrayList<Curso>();
		Map<Integer,Integer> maisRelacionados = new HashMap<Integer, Integer>();
		Map<Integer,Integer> maisUsuarios = new HashMap<Integer, Integer>();
		
		Scanner leitura = new Scanner(System.in);
		
		Usuario usu = null;
		Curso cur = null;
		Disciplina disc = null;
		Grupo grup = null;
		Conteudo cont = null;
		Disciplina disciplinaCadastro;
		Curso cursoCadastro;
		Area areaCadastro;
		Usuario usuarioAtual = null;
		boolean comecar = true, sair = true, voltar = true, entrou = false, prosseguir = false, cadastro = false, relacionar = false;
		String opcao = "", prontuario = "", senha, nome, tipo, titulo, grauConf[] = {"Conhecidos", "Amigos", "Melhores Amigos"};	
		int posicao = -1, semestre = 0, semestres = 0, grau, grauAtual = -1, novoGrau = -1, volta;
		
		while(comecar) {
			
			do {
				//pagina principal do sistema
				System.out.println("Bem vindo ao IFNET");
				System.out.println("1.Entrar\n2.Criar nova conta\nS.Sair");
				opcao = leitura.nextLine().toUpperCase();
				
				switch(opcao) {
				
					case "1":
						
						do {
							
							//recebe o prontuário
							System.out.print("Informe o prontuario: ");
							prontuario = leitura.nextLine().toUpperCase();
							
							//recebe a senha
							System.out.print("Informe a senha: ");
							senha = leitura.nextLine();
					
							usuarioAtual = Usuario.loginUsuario(usuarios, prontuario, senha);
							if(usuarioAtual  != null) {
								entrou = true;
								sair = false;
							} else {
								System.out.println("O prontuário e a senha fornecidos não correspondem as "
										+ "informações em nossos registros. Verifique-as e tente novamente.");
							}
						}while(usuarioAtual == null);
						
						break;
					case "2":
						
						System.out.println("Cadastre-se");
						
						try {
							
							System.out.print("Informe o prontuário: ");
							prontuario = leitura.nextLine().toUpperCase();
							
							if(Usuario.usuarioExistente(usuarios, prontuario)) {
								throw new UsuarioCadastradoException();
							}
							
						} catch (UsuarioCadastradoException excecao) {
							System.out.println(excecao.getMessage());
							break;
						} 
						
						System.out.print("Informe o nome: ");
						nome = leitura.nextLine();
							
						System.out.print("Informe a senha: ");
						senha = leitura.nextLine();
						
						do {
							
							System.out.println("Você é Aluno ou Professor?\n1 - Aluno\n2 - Professor");
							opcao = leitura.nextLine();
					
							switch(opcao) {
							
								case "1":
									
									System.out.print("Informe o e-mail: ");
									String email = leitura.nextLine();
									
									System.out.print("Informe o curso: ");
									String curso = leitura.nextLine();
									
									cursoCadastro = new Curso(curso);
									
									usuarios.add(new Aluno(nome, prontuario, senha, email, cursoCadastro));
									
									cadastro = true;
									
									System.out.println("Cadastro realizado com sucesso!");
									
									break;
								case "2":
									
									System.out.print("Informe a área: ");
									String area = leitura.nextLine();
									
									System.out.print("Informe a disciplina: ");
									String disciplina = leitura.nextLine();
									
									disciplinaCadastro = new Disciplina(disciplina);
									areaCadastro = new Area(area);
									
									disciplinas.add(disciplinaCadastro);
									areas.add(areaCadastro);
									
									usuarios.add(new Professor(nome, prontuario, senha,
											new Area(area), new Disciplina(disciplina)));
									
									cadastro = true;
									
									System.out.println("Cadastro realizado com sucesso!");
									
									break;
								default:
									System.out.println("Opção inválida");
							}
							
						} while(!cadastro);
			
						break;
					case "S":
						comecar = sair = false;
						break;
					default:
						System.out.println("Opção inválida");
				}
			}while(sair);
			
			sair = true;
			
			if(entrou) {
				
				do {
						
					voltar = true;
					
					System.out.println(usuarioAtual.getNome());
					System.out.println("1.Conteudo\n2.Usuários\n3.Grupo\n4.Conta");
					if(usuarioAtual.getClass() == Aluno.class) System.out.println("5.Disciplina\n6.Curso");
					System.out.println("S.Sair");
					opcao = leitura.nextLine().toUpperCase();
				
					switch(opcao) {
					
						case "1":
							
							do {
								
								if(conteudos.size() > 0) {
									
									do {
										
										if(conteudoAlt.size() == 0) {
											conteudoAlt = new ArrayList<Conteudo>(conteudos);
											System.out.println("Contéudos");
										}else System.out.println("Resultado da busca");
										
										for(Conteudo conteudo:conteudoAlt) {
											System.out.println(conteudoAlt.indexOf(conteudo) +". " + conteudo.getTitulo());
										}
										
										System.out.println("Informe o número do conteúdo desejado: ");
										
										try {
											
											posicao = Integer.parseInt(leitura.nextLine());
											
											if(posicao >= conteudoAlt.size() || posicao < 0) {
												throw new OpcaoInexistenteException();
											}
											
											prosseguir = true;
											cont = conteudoAlt.get(posicao);
											
										} catch (NumberFormatException excecao) {
											System.out.println("O valor informado não é um número inteiro");
										} catch (OpcaoInexistenteException excecao) {
											System.out.println(excecao.getMessage());
											conteudoAlt.clear();
										}
										
									}while(!prosseguir);
									
									System.out.println(conteudoAlt.get(posicao));
								}
								
								System.out.println("1.Pesquisar Conteúdo\n2.Publicar Conteúdo\n3.Excluir Conteúdo\nV.Voltar");
								opcao = leitura.nextLine().toUpperCase();
								
								conteudoAlt.clear();
								prosseguir = false;
								
								switch(opcao) {
								
									case "1":
										
										System.out.println("Informe o título do conteúdo: ");
										titulo = leitura.nextLine();
										
										conteudoAlt = Conteudo.pesquisarConteudos(conteudos, titulo);
										
										if(conteudoAlt.size() == 0) System.out.println("Não foi encontrado nenhum conteudo com o título informado");
										
										break;
									case "2":
										
										System.out.println("Informe o titulo do conteúdo");
										titulo = leitura.nextLine();

										System.out.println("Informe o tipo de conteúdo que deseja adicionar");
										tipo = leitura.nextLine();	
									
										conteudos.add(new Conteudo(titulo, tipo, usuarioAtual));
										
										System.out.println("Conteúdo publicado");
										break;
									case "3":
										
										if(conteudos.size() > 0) {
											
											if(cont.getPublicador().equals(usuarioAtual)) {
												
												do {
													
													System.out.println("Você tem certeza que deseja excluir o conteúdo? "
															+ "Essa ação não pode ser desfeita\n1.Sim\n2.Não");
													opcao = leitura.nextLine();
													
													switch(opcao) {
													
														case "1":
															conteudos.remove(cont);
															System.out.println("Conteúdo excluído");
															break;
														case "2":
															System.out.println("Conteúdo não excluído");
															break;
														default:
															System.out.println("Opção invàlida");
													}
												}while(!opcao.equals("1") && !opcao.equals("2"));
												
											} else System.out.println("Ação negada, somente o publicador do contéudo tem permissão para excluí-lo");
							
										}else System.out.println("Ação negada, não existem conteúdos cadastrados.");
							
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
								
								if(usuarios.size() > 1) {
									
									do {
										
										if(usuariosAlt.size() == 0) {
											usuariosAlt = new ArrayList<Usuario>(usuarios);
											System.out.println("Usuarios");
										}else System.out.println("Resultado da busca");
										
										for(Usuario usuario:usuariosAlt) {
											if(usuarios.indexOf(usuario) != usuarios.indexOf(usuarioAtual)){
												System.out.println(usuariosAlt.indexOf(usuario) +". " + usuario.getNome());
											}
										}
										
										System.out.println("Informe o número do usuário desejado: ");
										
										try {
											
											posicao = Integer.parseInt(leitura.nextLine());
											
											if(posicao >= usuariosAlt.size() || posicao < 0 || posicao == usuariosAlt.indexOf(usuarioAtual)) {
												throw new OpcaoInexistenteException();
											}
											
											usu = usuariosAlt.get(posicao);
											prosseguir = true;
											
										} catch (NumberFormatException excecao) {
											System.out.println("O valor informado não é um número inteiro");
										} catch (OpcaoInexistenteException excecao) {
											System.out.println(excecao.getMessage());
											usuariosAlt.clear();
										}
										
									}while(!prosseguir);
									
									System.out.println(usuariosAlt.get(posicao));
								}
								
								System.out.println("1.Pesquisar Usuários\n2.Relacionar Usuários\n3.Alterar grau de confiabilidade\n4.Excluir Relacionamento"
										+ "\n5.Consultar usuário com mais relacionamentos\nV.Voltar");
								opcao = leitura.nextLine().toUpperCase();
								
								usuariosAlt.clear();
								prosseguir = false;
								
								switch(opcao) {
									case "1":
										
										System.out.println("Pesquisar Usuário");
										
										System.out.println("Informe o nome do Usuário: ");
										nome = leitura.nextLine();
										
										usuariosAlt = Usuario.pesquisarUsuario(usuarios, nome);
										
										if(usuariosAlt.size() == 0) System.out.println("Não foi encontrado nenhum usuário com o nome informado");
										
										break;
									case "2":
										
										relacionar = Relacionamento.relacionarUsuario(usuarioAtual, usuarios.get(usuarios.indexOf(usu)));
										
										if(relacionar) System.out.println("O relacionamento foi criado");
										else System.out.println("O usuário atual e o usuário informado já estão relacionados");
										
										break;
									case "3":

										if(Relacionamento.estaRelacionado(usuarioAtual, usu)) {
											
											for (Map.Entry<Integer , ArrayList<Usuario>> mapa : usuarioAtual.getRelacionamento().getGrauUsuario().entrySet()) { 
												
												usuariosMapa = mapa.getValue();
												grau = mapa.getKey();
												
												for(Usuario usuario:usuariosMapa) {
													if(usuario.getProntuario().equals(usu.getProntuario())) {
														grauAtual = grau;
														posicao = usuariosMapa.indexOf(usuario);
													}
												}
											}
											
											do{

												try {
													
													System.out.println("Grau de Confiabilidade Atual: " + grauConf[grauAtual]);
													
													System.out.println("Graus de confiabilidade\n0.Conhecidos\n1.Amigos\n2.Amigos Próximos");
													
													System.out.print("Informe o número do grau de confiabilidade para o qual a relação será alterada: ");
													novoGrau = Integer.parseInt(leitura.nextLine());
													
													if(novoGrau == grauAtual) {
														throw new GrauConfiabilidadeAtualException();
													}
													
													if(novoGrau > 2 || novoGrau < 0) {
														throw new OpcaoInexistenteException();
													}

													prosseguir = true;
													
												} catch (NumberFormatException excecao) {
													System.out.println("O valor informado não é um número inteiro");
												} catch (GrauConfiabilidadeAtualException excecao) {
													System.out.println(excecao.getMessage());
												} catch (OpcaoInexistenteException excecao) {
													System.out.println(excecao.getMessage());
													conteudoAlt.clear();
												}

											}while(!prosseguir);

											prosseguir = false;
											
											Relacionamento.alterarGrauConfiabilidade(usuarioAtual, grauAtual, novoGrau, posicao);
											
											System.out.println("Grau de Confiabilidade Alterado");
											
										}else System.out.println("Esses usuários não estão relacionados");
										
										break;
									case "4":
										
										if(Relacionamento.estaRelacionado(usuarioAtual, usu)) {

											for (Map.Entry<Integer , ArrayList<Usuario>> mapa : usuarioAtual.getRelacionamento().getGrauUsuario().entrySet()) { 
																					
												usuariosMapa = mapa.getValue();
												grau = mapa.getKey();
												
												for(Usuario usuario:usuariosMapa) {
													if(usuario.getProntuario().equals(usu.getProntuario())) {
														grauAtual = grau;
														posicao = usuariosMapa.indexOf(usuario);
													}
												}
											}
											
											do {
												
												System.out.println("Você tem certeza que deseja excluir o relacionamento? "
														+ "\n1.Sim\n2.Não");
												opcao = leitura.nextLine();
												
												switch(opcao) {
												
													case "1":
														usuarioAtual.getRelacionamento().getGrauUsuario().get(grauAtual).remove(posicao);
														System.out.println("Relacionamento excluído");
														break;
													case "2":
														System.out.println("Relacionamento não excluído");
														break;
													default:
														System.out.println("Opção invàlida");
												}

											}while(!opcao.equals("1") && !opcao.equals("2"));
												
										}else System.out.println("Você e o usuário selecionado não possuem relação");

										break;
									case "5":
										
										maisRelacionados = Relacionamento.consultarUsuariosMaisRelacionado(usuarios);
										
										System.out.println("TOP 10 Usuários mais relacionados");
										
										volta = 0;
										
										for (Map.Entry<Integer , Integer> mapa : maisRelacionados.entrySet()) {
											System.out.println("Usuário: " + usuarios.get(mapa.getKey()).getNome() + "\nQuantidade de relacionamentos: " + mapa.getValue() + "\n");
											volta++;
											if(volta == 9) break;
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
						case "3":
							
							do {
								
								if(grupos.size() > 0) {
									
									do {
										
										if(grupoAlt.size() == 0) {
											grupoAlt = new ArrayList<Grupo>(grupos);
											System.out.println("Grupos");
										}else System.out.println("Resultado da busca");
										
										for(Grupo grupo:grupoAlt) {
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
								if(usuarioAtual.getClass() == Professor.class) 
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
										
										grupoAlt = Grupo.pesquisarGrupos(grupos, nome);
										
										if(grupoAlt.size() == 0) System.out.println("Não foi encontrado nenhum grupo com o nome informado");
										
										break;
									case "2":
										
										do {
											
											System.out.println("Consultar Grupo de Pesquisa por Disciplina");
											
											System.out.println("Disciplinas");
											
											for(Disciplina disciplina:disciplinas) {
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
										
										grupoAlt = Grupo.consultarGpPesquisaPorDisciplina(grupos, disciplinas.get(posicao));
										
										if(grupoAlt.size() == 0) System.out.println("Não foi encontrado nenhum grupo de pesquisa com a disciplina informada");
									
										prosseguir = false;

										break;
										
									case "3":
										
										maisUsuarios = Grupo.consultarGrupoMaisUsuarios(grupos);
										
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
								
								if(usuarioAtual.getClass() == Professor.class) {
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
												
												for(Disciplina disciplina:disciplinas) {
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
												
											grupos.add(new Grupo(nome, disciplinas.get(posicao), (Professor) usuarioAtual, tipo));
											
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
								
								if(usuarioAtual.getClass() == Professor.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4") && !opcao.equals("5")) && !opcao.equals("6")|| 
										usuarioAtual.getClass() == Aluno.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4"))) {
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
					
					if(usuarioAtual.getClass() == Aluno.class) {
						
						switch(opcao) {
							
						case "5":
							
							do {
								
								if(disciplinas.size() > 0) {
									
									do {
										
										if(disciplinaAlt.size() == 0) {
											disciplinaAlt = new ArrayList<Disciplina>(disciplinas);
											System.out.println("Disciplinas");
										}else System.out.println("Resultado da busca");
										
										for(Disciplina disciplina:disciplinaAlt) {
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
										
										disciplinaAlt = Disciplina.pesquisarDisciplinas(disciplinas, nome);
										
										if(disciplinaAlt.size() == 0) System.out.println("Não foi encontrado nenhuma disciplina com o nome informado");
								
										break;
									case "2":
										
											System.out.println("Informe O nome da Diciplina que deseja adicionar");
											nome = leitura.nextLine();
											
											disciplinas.add(new Disciplina(nome));
											
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
											cursoAlt = new ArrayList<Curso>(cursos);
											System.out.println("Curso");
										}else System.out.println("Resultado da busca");
										
										for(Curso curso:cursoAlt) {
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
										
										cursoAlt = Curso.pesquisaCurso(cursos, nome);
										
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
										
										cur = new Curso(nome, semestres);
										
										do {
											
											do {
											
												System.out.println("Disciplina");
												
												for(Disciplina disciplina:disciplinas) {
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
					
					if(usuarioAtual.getClass() == Aluno.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4") && !opcao.equals("5") && !opcao.equals("6") && !opcao.equals("V")) || 
							usuarioAtual.getClass() == Professor.class && (!opcao.equals("1") && !opcao.equals("2") && !opcao.equals("3") && !opcao.equals("4")) && !opcao.equals("V")){
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
		