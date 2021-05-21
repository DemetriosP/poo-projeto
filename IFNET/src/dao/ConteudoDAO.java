package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import ifnet.Conteudo;

public class ConteudoDAO {
	
	public void insere(Conteudo conteudo) {		
		
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

}
