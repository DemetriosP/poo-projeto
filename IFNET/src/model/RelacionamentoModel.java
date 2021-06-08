package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RelacionamentoModel {
	
	private Map<String, ArrayList<UsuarioModel>> grauUsuario = new HashMap<>();
	
	public RelacionamentoModel() {
		this.criarMapa();
	}
	
	public Map<String, ArrayList<UsuarioModel>> getGrauUsuario() {
		return grauUsuario;
	}
	
	public void setGrauUsuario(String grauRelacionamento, UsuarioModel usuarioRelacioando) {
		this.grauUsuario.get(grauRelacionamento).add(usuarioRelacioando);
	}

	public void setGrauUsuario(Map<String, ArrayList<UsuarioModel>> grauUsuario) {
		this.grauUsuario = grauUsuario;
	}
	
	private void criarMapa() {
		this.grauUsuario.put("Conhecidos", new ArrayList<>());
		this.grauUsuario.put("Amigos", new ArrayList<>());
		this.grauUsuario.put("Amigos Próximos", new ArrayList<>());
	}
	
	public static boolean relacionarUsuario(UsuarioModel usuarioAtual, UsuarioModel usuarioRelacionar) {
		
		RelacionamentoModel relacionar;
		ArrayList<UsuarioModel> usuarios;
		
		relacionar = usuarioAtual.getRelacionamento();
		
		for (Map.Entry<String , ArrayList<UsuarioModel>> mapa : relacionar.grauUsuario.entrySet()) { 
			
			usuarios = mapa.getValue();
			
			for(UsuarioModel usuario:usuarios) {
				if(usuario.getProntuario().equals(usuarioRelacionar.getProntuario())) return false;
			}
		}
		
		usuarioRelacionar.getRelacionamento().getGrauUsuario().get("Amigos").add(usuarioAtual);
		relacionar.grauUsuario.get("Amigos").add(usuarioRelacionar);
		return true;
		
	}
	
	public static String getGrauRelacionamento(UsuarioModel usuarioAtual, String prontuario) {
		
		ArrayList<UsuarioModel> usuarios;
		
		for (Map.Entry<String , ArrayList<UsuarioModel>> mapa : usuarioAtual.getRelacionamento().grauUsuario.entrySet()) { 
			
			usuarios = mapa.getValue();
			
			for(UsuarioModel usuario:usuarios) {
				if(usuario.getProntuario().equals(prontuario)) {
					return mapa.getKey();
				}
			}
		}
		
		return null;
		
	}
	
	
	
}
