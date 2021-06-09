package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.AreaModel;
import model.DisciplinaModel;
import model.ProfessorModel;
import model.UsuarioModel;

public class ProfessorDAO {
	
	public static void inserirProfessor(ProfessorModel professor) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into professor (usuario_id, area_id, disciplina_id) values (?,?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			UsuarioDAO.insereUsuario(professor);
			
			statement.setString(1, professor.getProntuario());
			statement.setString(2, professor.getArea().getNome());
			statement.setString(3, professor.getDisciplMinistrada().getNome());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean eProfessor(String usuarioID) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		try {
			
			String query = "select * from professor where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuarioID);
		
			resultado = statement.executeQuery();

			return resultado != null && resultado.next();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public static ArrayList<UsuarioModel> selecionarProfessores(){
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		ArrayList<UsuarioModel> professor = new ArrayList<>();
		
		String usuarioID, areaID, disciplinaID;
		UsuarioModel usuario;
		
		try {
			
			String query = "select * from professor";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				usuarioID = resultado.getString("usuario_id");
				areaID = resultado.getString("area_id");
				disciplinaID = resultado.getString("disciplina_id");
				
				usuario = UsuarioDAO.selecionaUsuario(usuarioID);
				
				professor.add(new ProfessorModel(usuario.getNome(), usuario.getProntuario() ,usuario.getSenha(), 
						new AreaModel(areaID), new DisciplinaModel(disciplinaID)));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professor;
		
	}
	
	public static ProfessorModel selecionarProfessor(String usuarioID){
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		ProfessorModel professor = null;
		
		String areaID, disciplinaID;
		UsuarioModel usuario;
		
		try {
			
			String query = "select * from professor where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuarioID);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				areaID = resultado.getString("area_id");
				disciplinaID = resultado.getString("disciplina_id");
				
				usuario = UsuarioDAO.selecionaUsuario(usuarioID);
				
				professor = new ProfessorModel(usuario.getNome(), usuario.getProntuario() ,usuario.getSenha(), 
						new AreaModel(areaID), new DisciplinaModel(disciplinaID));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professor;
		
	}
	
	public static void excluirProfessor(UsuarioModel usuario) {
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "delete from professor where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuario.getProntuario());
			
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		UsuarioDAO.excluirUsuario(usuario);
	}

}
