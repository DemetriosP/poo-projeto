package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ifnet.Aluno;
import ifnet.Conteudo;
import ifnet.Professor;

public class ConteudoDAO {
	
	public void insereConteudo(Conteudo conteudo) {		
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "insert into conteudo (titulo, tipo, usuario_id) values (?,?,?,?)";
			
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
	
	public ArrayList<Conteudo> selecionaConteudos() {
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		ArrayList<Conteudo> conteudos = new ArrayList<Conteudo>();
		String titulo, tipo, usuario;
		
		try {
			
			String query = "select titulo, tipo, usuario_id from conteudo";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				
				titulo = resultado.getString("titulo");
				tipo = resultado.getString("tipo");
				usuario = resultado.getString("usuario_id");
				
				if(AlunoDAO.eAluno(usuario)) conteudos.add( new Conteudo(titulo, tipo, new Aluno(usuario)));
				else if(ProfessorDAO.eProfessor(usuario)) conteudos.add( new Conteudo(titulo, tipo, new Professor(usuario)));
				
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conteudos;
	}

}
