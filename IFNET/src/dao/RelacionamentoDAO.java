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
		
		for (Map.Entry<Integer , ArrayList<UsuarioModel>> mapa : usuario.getRelacionamento().getGrauUsuario().entrySet()) {
		
			for(UsuarioModel usuarios:mapa.getValue()) {
				
				try {
					
					String query = "insert into relacionamento (grau_relacionamento, usuario_relacionamento, "
							+ "usuario_relacionado) values (?,?,?)";
					
					PreparedStatement statement = conexao.getConexao().prepareStatement(query);
					
					statement.setInt(1, mapa.getKey());
					statement.setString(2, usuario.getProntuario());
					statement.setString(3, usuarios.getProntuario());
					statement.execute();
					statement.close();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
	public static RelacionamentoModel selecionarRelacionamento(UsuarioModel usuario){
		
		Conexao conexao = new Conexao();
		ResultSet resultado = null;
		
		RelacionamentoModel relacionamento = new RelacionamentoModel();
		int grauRelacionamento;
		String usuarioRelacionado;
		UsuarioModel usuarioRel = null;
		
		try {
			
			String query = "select grau_relacionamento, usuario_relacionado from realcionamento where usuario_relaciona like ?";
			
			PreparedStatement statement = conexao.getConexao().prepareStatement(query);
			
			statement.setString(1, usuario.getProntuario());

			resultado = statement.executeQuery();
			
			while(resultado != null && resultado.next()){
				
				grauRelacionamento = resultado.getInt("grau_relacionamento");
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
	
	

}
