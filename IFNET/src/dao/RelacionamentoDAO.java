package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import model.RelacionamentoModel;
import model.UsuarioModel;

public class RelacionamentoDAO {
	
	public static void inserirRelacionamento(UsuarioModel usuario) {		
		
		Conexao conexao = new Conexao();
		
		for (Map.Entry<String , ArrayList<UsuarioModel>> mapa : usuario.getRelacionamento().getGrauUsuario().entrySet()) {
		
			for(UsuarioModel usuarios:mapa.getValue()) {
				
				if(eRelacionado(usuario.getProntuario(), usuarios.getProntuario())) {
					
					try {
						
						String query = "insert into relacionamento (grau_relacionamento, usuario_relaciona, " +
								"usuario_relacionado) values (?,?,?), (?,?,?);";
						
						PreparedStatement statement = conexao.getConexao().prepareStatement(query);
						
						statement.setString(1, mapa.getKey());
						statement.setString(2, usuario.getProntuario());
						statement.setString(3, usuarios.getProntuario());
						statement.setString(4, mapa.getKey());
						statement.setString(5, usuarios.getProntuario());
						statement.setString(6, usuario.getProntuario());
						statement.execute();
						statement.close();
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}
				
			}
			
		}
		
	}
	
	public static RelacionamentoModel selecionarRelacionamento(UsuarioModel usuario){
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		RelacionamentoModel relacionamento = new RelacionamentoModel();
		String usuarioRelacionado, grauRelacionamento;
		UsuarioModel usuarioRel = null;
		
		try {
			
			String query = "select grau_relacionamento, usuario_relacionado from relacionamento where usuario_relaciona like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuario.getProntuario());

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				
				grauRelacionamento = resultado.getString("grau_relacionamento");
				usuarioRelacionado = resultado.getString("usuario_relacionado");
				
				if(AlunoDAO.eAluno(usuarioRelacionado)) {
					usuarioRel = AlunoDAO.selecionarAluno(usuarioRelacionado);
				}else if(ProfessorDAO.eProfessor(usuarioRelacionado)) {
					usuarioRel = ProfessorDAO.selecionarProfessor(usuarioRelacionado);
				}
				
				relacionamento.setGrauUsuario(grauRelacionamento, usuarioRel);
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return relacionamento;
		
	}
	
	public static boolean eRelacionado(String usuarioRelaciona, String usuarioRelacionado) {
		
		Conexao conexao = new Conexao();
		ResultSet resultado;
		
		try {
			
			String query = "select * from relacionamento where usuario_relaciona like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuarioRelaciona);
		
			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()) {
				
				if(resultado.getString("usuario_relacionado").equals(usuarioRelacionado)) {
					return true;
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void alterarGrauRelacionamento(String relaciona, String relacionado, String grau) {
		
		Conexao conexao = new Conexao();
			
		try {
			
			String query = "update relacionamento set grau_relacionamento = ? where usuario_relaciona like ? and usuario_relacionado like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, grau);
			statement.setString(2, relaciona);
			statement.setString(3, relacionado);
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
					
	}
	
	public static void excluirRelacionamento(String relaciona, String relacionado) {
		
		Conexao conexao = new Conexao();	
		
		try {
			
			String query = "delete from relacionamento where usuario_relaciona like ? and usuario_relacionado like ? "
					+ "or usuario_relaciona like ? and usuario_relacionado like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, relaciona);
			statement.setString(2, relacionado);
			statement.setString(3, relacionado);
			statement.setString(4, relaciona);
			
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<String[]> usuariosMaisRelacionados() {
		
		Conexao conexao = new Conexao();	
		ResultSet resultado;
		
		ArrayList<String[]> dados = new ArrayList<>();
		
		int voltas = 0;
		
		try {
			
			String query = "select usuario_relaciona, count(*) from relacionamento group by usuario_relaciona having count(*) > 0 order by count(*) desc";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			resultado = statement.executeQuery();
			
			while((resultado != null && resultado.next()) && voltas < 10) {
				
				String[] relacionamento = new String [2];
				
				relacionamento[0] = resultado.getString("usuario_relaciona");
				relacionamento[1] = resultado.getString("count(*)");
			
				dados.add(relacionamento);
				voltas++;
			}
			
			statement.execute();
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dados;
	}

}
