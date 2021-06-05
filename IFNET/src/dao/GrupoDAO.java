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

	private static int selecionaGrupoID(GrupoModel grupo) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
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
		ResultSet resultado = null;
		
		ArrayList<GrupoModel> grupos = new ArrayList<GrupoModel>();
		GrupoModel grupo = null;
		String nome, disciplina, usuario, tipo;
		
		try {
			
			String query = "select * from grupo";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				nome = resultado.getString("nome");
				disciplina = resultado.getString("disciplina_id");
				usuario = resultado.getString("usuario_id");
				tipo = resultado.getString("tipo");
				
				grupo = new GrupoModel(nome, new DisciplinaModel(disciplina), ProfessorDAO.selecionarProfessor(usuario), tipo);
				grupos.get(grupos.indexOf(grupo)).setUsuariosGrupo(selecionaUsuariosGrupo(grupo));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return grupos;
	}
	
	private static ArrayList<UsuarioModel> selecionaUsuariosGrupo(GrupoModel grupo) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		int grupoID = selecionaGrupoID(grupo);
		
		ArrayList<UsuarioModel> usuarios = new ArrayList<UsuarioModel>();
		
		String usuarioID = null;
		
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

}
