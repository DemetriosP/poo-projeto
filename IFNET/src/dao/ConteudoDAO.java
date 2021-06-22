package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ConteudoModel;

public class ConteudoDAO {
	
	public static void insereConteudo(ConteudoModel conteudo) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into conteudo (titulo, tipo, usuario_id) values (?,?,?)";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, conteudo.getTitulo());
			statement.setString(2, conteudo.getTipoConteudo());
			statement.setString(3, conteudo.getPublicador().getProntuario());
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<ConteudoModel> selecionaConteudos() {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		ArrayList<ConteudoModel> conteudos = new ArrayList<>();
		String titulo, tipo, usuarioID;
		int codigo;
		
		try {
			
			String query = "select * from conteudo";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				
				codigo = resultado.getInt("conteudo_id");
				titulo = resultado.getString("titulo");
				tipo = resultado.getString("tipo");
				usuarioID = resultado.getString("usuario_id");
				
				if(AlunoDAO.eAluno(usuarioID)) {
					conteudos.add( new ConteudoModel(titulo, tipo, AlunoDAO.selecionarAluno(usuarioID), codigo));
				} else if(ProfessorDAO.eProfessor(usuarioID)) {
					conteudos.add( new ConteudoModel(titulo, tipo, ProfessorDAO.selecionarProfessor(usuarioID), codigo));
				}
				
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conteudos;
	}
	
	public static ArrayList<ConteudoModel> selecionaConteudos(String titulo){
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		ArrayList<ConteudoModel> conteudos = new ArrayList<>();
		String tipo, usuarioID;
		int codigo;
		
		try {
			
			String query = "select * from conteudo where titulo like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, "%" + titulo + "%");
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				
				codigo = resultado.getInt("conteudo_id");
				titulo = resultado.getString("titulo");
				tipo = resultado.getString("tipo");
				usuarioID = resultado.getString("usuario_id");
				
				if(AlunoDAO.eAluno(usuarioID)) {
					conteudos.add( new ConteudoModel(titulo, tipo, AlunoDAO.selecionarAluno(usuarioID), codigo));
				} else if(ProfessorDAO.eProfessor(usuarioID)) {
					conteudos.add( new ConteudoModel(titulo, tipo, ProfessorDAO.selecionarProfessor(usuarioID), codigo));
				}
				
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conteudos;
		
	} 
	
	public static String selecionarConteudo(int codigo) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		String usuarioID = null;
		
		try {
			
			String query = "select usuario_id from conteudo where conteudo_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, codigo);
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				usuarioID = resultado.getString("usuario_id");
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usuarioID;
	}
	
	
	public static ArrayList<ConteudoModel> pesquisarConteudos(String palavra) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		ArrayList<ConteudoModel> conteudos = new ArrayList<>();
		String titulo, tipo, usuarioID;
		int codigo;
		
		try {
			
			String query = "select * from conteudo where titulo like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, "%" + palavra + "%");
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				
				codigo = resultado.getInt("conteudo_id");
				titulo = resultado.getString("titulo");
				tipo = resultado.getString("tipo");
				usuarioID = resultado.getString("usuario_id");
				
				if(AlunoDAO.eAluno(usuarioID)) {
					conteudos.add( new ConteudoModel(titulo, tipo, AlunoDAO.selecionarAluno(usuarioID), codigo));
				} else if(ProfessorDAO.eProfessor(usuarioID)) {
					conteudos.add( new ConteudoModel(titulo, tipo, ProfessorDAO.selecionarProfessor(usuarioID), codigo));
				}
				
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conteudos;
	}
	
	public static void excluirConteudo(int codigo) {
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "delete from conteudo where conteudo_id like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setInt(1, codigo);
			
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
