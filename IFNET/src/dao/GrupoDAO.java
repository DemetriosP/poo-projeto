package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import model.DisciplinaModel;
import model.GrupoModel;
import model.UsuarioModel;

public class GrupoDAO {
	
	public static void inserirGrupo(GrupoModel grupo) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into grupo (nome, disciplina_id, usuario_id, tipo) values (?,?,?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, grupo.getNome());
			statement.setString(2, grupo.getDisciplina().getNome());
			statement.setString(3, grupo.getCriador().getProntuario());
			statement.setString(4, grupo.getTipo());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		inserirUsuariosGrupo(grupo, grupo.getCriador());
	}
	
	public static void inserirUsuariosGrupo(GrupoModel grupo, UsuarioModel usuario) {
		
		Conexao conexao = new Conexao();
		
		int grupoID = selecionaGrupoID(grupo);
		
		try {
			
			String query = "insert into usuarios_grupo (grupo_id, usuario_id) values (?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, grupoID);
			statement.setString(2, usuario.getProntuario());
			statement.execute();
			statement.close();
			
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Erro");
		} catch (SQLException e) {
			System.out.println("Erro2");
		}
	}
	
	public static void inserirUsuariosGrupo(int grupoID, UsuarioModel usuario) {
		
		Conexao conexao = new Conexao();
		
		try {
			
			String query = "insert into usuarios_grupo (grupo_id, usuario_id) values (?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, grupoID);
			statement.setString(2, usuario.getProntuario());
			statement.execute();
			statement.close();
			
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Erro");
		} catch (SQLException e) {
			System.out.println("Erro2");
		}
	}
	
	private static int selecionaGrupoID(GrupoModel grupo) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		int grupoID = 0;
		
		try {
			
			String query = "select grupo_id from grupo where nome like ? and disciplina_id like ? "
					+ "and usuario_id like ? and tipo like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, grupo.getNome());
			statement.setString(2, grupo.getDisciplina().getNome());
			statement.setString(3, grupo.getCriador().getProntuario());
			statement.setString(4, grupo.getTipo());

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				grupoID = resultado.getInt("grupo_id");
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return grupoID;
	}
	
	public static ArrayList<GrupoModel> selecionaGrupo() {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		ArrayList<GrupoModel> grupos = new ArrayList<>();
		GrupoModel grupo;
		String nome, disciplina, usuario, tipo;
		int grupoID;
		
		try {
			
			String query = "select * from grupo";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				grupoID = resultado.getInt("grupo_id");
				nome = resultado.getString("nome");
				disciplina = resultado.getString("disciplina_id");
				usuario = resultado.getString("usuario_id");
				tipo = resultado.getString("tipo");
				
				grupo = new GrupoModel(nome, new DisciplinaModel(disciplina), ProfessorDAO.selecionarProfessor(usuario), tipo);
				grupo.setCodigo(grupoID);
				grupo.setUsuariosGrupo(selecionaUsuariosGrupo(grupoID));
				grupos.add(grupo);
				
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return grupos;
	}
	
	private static ArrayList<UsuarioModel> selecionaUsuariosGrupo(int grupoID) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		ArrayList<UsuarioModel> usuarios = new ArrayList<>();
		
		String usuarioID;
		
		try {
			
			String query = "select usuario_id from usuarios_grupo where grupo_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, grupoID);
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				usuarioID = resultado.getString("usuario_id");
				
				if(AlunoDAO.eAluno(usuarioID)) {
					usuarios.add(AlunoDAO.selecionarAluno(usuarioID));
				} else if(ProfessorDAO.eProfessor(usuarioID)){
					usuarios.add(ProfessorDAO.selecionarProfessor(usuarioID));
				}
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usuarios;
	}
	
	public static ArrayList<GrupoModel> selecionaGrupoPorDisciplina(String disciplinaID) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		ArrayList<GrupoModel> grupos = new ArrayList<>();
		GrupoModel grupo;
		String nome, disciplina, usuario, tipo;
		int grupoID;
		
		try {
			
			String query = "select * from grupo where disciplina_id like ? and tipo like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, disciplinaID);
			statement.setString(2, "Pesquisa");
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				grupoID = resultado.getInt("grupo_id");
				nome = resultado.getString("nome");
				disciplina = resultado.getString("disciplina_id");
				usuario = resultado.getString("usuario_id");
				tipo = resultado.getString("tipo");
				
				grupo = new GrupoModel(nome, new DisciplinaModel(disciplina), ProfessorDAO.selecionarProfessor(usuario), tipo);
				grupo.setCodigo(grupoID);
				grupo.setUsuariosGrupo(selecionaUsuariosGrupo(grupoID));
				grupos.add(grupo);
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return grupos;
	}
	
	public static Object[][] consultarGruposMaisUsuariosIG() {
		
		Conexao conexao = new Conexao();	
		ResultSet resultado;
		
		Object[][] dados = new Object[10][2];
		
		int voltas = 0;
		
		try {
			
			String query = "select grupo_id, count(*) from usuarios_grupo group by grupo_id having count(*) > 0 order by count(*) desc";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			resultado = statement.executeQuery();
			
			while((resultado != null && resultado.next()) && voltas < 10) {
				
				dados[voltas][0] = selecionarGrupo(resultado.getInt("grupo_id")).getNome();
				dados[voltas][1] = resultado.getString("count(*)");
			
				voltas++;
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dados;
	}
	
	public static GrupoModel selecionarGrupo(int grupoID) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		GrupoModel grupo = null;
		String nome, disciplina, usuario, tipo;
		
		try {
			
			String query = "select * from grupo where grupo_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, grupoID);
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				nome = resultado.getString("nome");
				disciplina = resultado.getString("disciplina_id");
				usuario = resultado.getString("usuario_id");
				tipo = resultado.getString("tipo");
				
				grupo = new GrupoModel(nome, new DisciplinaModel(disciplina), ProfessorDAO.selecionarProfessor(usuario), tipo);
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return grupo;
	}
	
	public static boolean grupoExiste(int grupoID) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		boolean existe = false;
		
		try {
			
			String query = "select * from grupo where grupo_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, grupoID);
		
			resultado = statement.executeQuery();
			
			if(resultado != null && resultado.next()) existe = true;
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return existe;
	}
	
	public static boolean usuarioPresente(int grupoID, UsuarioModel usuarioAtual) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		boolean presente = false;
		
		try {
			
			String query = "select * from usuarios_grupo where grupo_id like ? and usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, grupoID);
			statement.setString(2, usuarioAtual.getProntuario());
		
			resultado = statement.executeQuery();
			
			if(resultado != null && resultado.next())presente = true;
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return presente;	
	}
	
	public static void excluirGrupo(int codigo) {
		
		Conexao conexao = new Conexao();
		excluirUsuariosGrupo(codigo);
		
		try {
			
			String query = "delete from grupo where grupo_id = ?;";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, codigo);
			
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void excluirUsuariosGrupo(int codigo) {
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "delete from usuarios_grupo where grupo_id = ?;";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, codigo);
			
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
public static ArrayList<String[]> consultarGruposMaisUsuarios() {
		
		Conexao conexao = new Conexao();	
		ResultSet resultado;
		
		ArrayList<String[]> dados = new ArrayList<>();
		
		int voltas = 0;
		
		try {
			
			String query = "select grupo_id, count(*) from usuarios_grupo group by grupo_id having count(*) > 0 order by count(*) desc";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			resultado = statement.executeQuery();
			
			while((resultado != null && resultado.next()) && voltas < 10) {
				
				String[] relacionamento = new String [2];
				
				relacionamento[0] = resultado.getString("grupo_id");
				relacionamento[1] = resultado.getString("count(*)");
			
				dados.add(relacionamento);
				voltas++;
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dados;
	}

}
