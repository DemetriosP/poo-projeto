package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ifnet.Area;
import ifnet.Disciplina;
import ifnet.Professor;
import ifnet.Usuario;

public class ProfessorDAO {
	
	public void insere(Professor professor) {		
		
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
	
	public static boolean eProfessor(String prontuario) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		try {
			
			String query = "select * from professor where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, prontuario);
		
			resultado = statement.executeQuery();
			
			if(resultado != null && resultado.next())return true;
			else return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public ArrayList<Usuario> selecionarProfessores(){
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		ArrayList<Usuario> professor = new ArrayList<Usuario>();
		
		String usuarioID, areaID, disciplinaID;
		String[] usuario;
		
		try {
			
			String query = "select * from professor";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				usuarioID = resultado.getString("usuario_id");
				areaID = resultado.getString("area_id");
				disciplinaID = resultado.getString("disciplina_id");
				
				usuario = UsuarioDAO.selecionaUsuario(usuarioID);
				
				professor.add(new Professor(usuario[0], usuarioID ,usuario[1], new Area(areaID), new Disciplina(disciplinaID)));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professor;
		
	}
	
	public static Professor selecionarProfessor(String usuarioID){
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		Professor professor = null;
		
		String areaID, disciplinaID;
		String[] usuario;
		
		try {
			
			String query = "select area_id, disciplina_id from professor where usuario_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuarioID);

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				areaID = resultado.getString("area_id");
				disciplinaID = resultado.getString("disciplina_id");
				
				usuario = UsuarioDAO.selecionaUsuario(usuarioID);
				
				professor = new Professor(usuario[0], usuarioID ,usuario[1], new Area(areaID), new Disciplina(disciplinaID));
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professor;
		
	}

}
