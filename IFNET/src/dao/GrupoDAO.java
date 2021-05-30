package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import ifnet.Disciplina;
import ifnet.Grupo;
import ifnet.Usuario;

public class GrupoDAO {
	
	public void inserirGrupo(Grupo grupo) {		
		
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
	
	public void inserirUsuariosGrupo(Grupo grupo, Usuario usuario) {
		
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

	private int selecionaGrupoID(Grupo grupo) {
		
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
	
	public ArrayList<Grupo> selecionaGrupo() {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		ArrayList<Grupo> grupos = new ArrayList<Grupo>();
		Grupo grupo = null;
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
				
				grupo = new Grupo(nome, new Disciplina(disciplina), ProfessorDAO.selecionarProfessor(usuario), tipo);
				grupos.get(grupos.indexOf(grupo)).setUsuariosGrupo(selecionaUsuariosGrupo(grupo));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return grupos;
	}
	
	private ArrayList<Usuario> selecionaUsuariosGrupo(Grupo grupo) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		int grupoID = selecionaGrupoID(grupo);
		
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		
		String usuario = null;
		
		try {
			
			String query = "select usuario_id from usuarios_grupo where grupo_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, grupoID);
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				usuario = resultado.getString("usuario_id");
				
				if(AlunoDAO.eAluno(usuario)) {
					usuarios.add(AlunoDAO.selecionarAluno(usuario));
				} else if(ProfessorDAO.eProfessor(usuario)){
					usuarios.add(ProfessorDAO.selecionarProfessor(usuario));
				}
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usuarios;
	}

}
