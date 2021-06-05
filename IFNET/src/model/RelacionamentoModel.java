package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RelacionamentoModel {
	
	private Map<Integer, ArrayList<UsuarioModel>> grauUsuario = new HashMap<Integer, ArrayList<UsuarioModel>>();
	
	public RelacionamentoModel() {
		this.criarMapa();
	}
	
	public Map<Integer, ArrayList<UsuarioModel>> getGrauUsuario() {
		return grauUsuario;
	}
	
	public void setGrauUsuario(int grauRelacionamento, UsuarioModel usuarioRelacioando) {
		this.grauUsuario.get(grauRelacionamento).add(usuarioRelacioando);
	}

	public void setGrauUsuario(Map<Integer, ArrayList<UsuarioModel>> grauUsuario) {
		this.grauUsuario = grauUsuario;
	}
	
	private void criarMapa() {
		this.grauUsuario.put(0, new ArrayList<UsuarioModel>());
		this.grauUsuario.put(1, new ArrayList<UsuarioModel>());
		this.grauUsuario.put(2, new ArrayList<UsuarioModel>());
	}
	
	public static boolean relacionarUsuario(UsuarioModel usuarioAtual, UsuarioModel usuarioRelacionar) {
		
		RelacionamentoModel relacionar = null;
		ArrayList<UsuarioModel> usuarios;
		
		relacionar = usuarioAtual.getRelacionamento();
		
		for (Map.Entry<Integer , ArrayList<UsuarioModel>> mapa : relacionar.grauUsuario.entrySet()) { 
			
			usuarios = mapa.getValue();
			
			for(UsuarioModel usuario:usuarios) {
				if(usuario.getProntuario().equals(usuarioRelacionar.getProntuario())) return false;
			}
		}
		
		usuarioRelacionar.getRelacionamento().getGrauUsuario().get(1).add(usuarioAtual);
		relacionar.grauUsuario.get(1).add(usuarioRelacionar);
		return true;
		
	}
	
	public static void alterarGrauConfiabilidade(UsuarioModel usuarioAtual, int grauAtual, int novoGrau, int posicaoRelacao) {
		
		UsuarioModel usuario;
		
		usuario = usuarioAtual.getRelacionamento().grauUsuario.get(grauAtual).get(posicaoRelacao);
		
		usuarioAtual.getRelacionamento().grauUsuario.get(grauAtual).remove(posicaoRelacao);
		
		usuarioAtual.getRelacionamento().grauUsuario.get(novoGrau).add(usuario);
		
	}

	public static Map<Integer, Integer> consultarUsuariosMaisRelacionado(ArrayList<UsuarioModel> usuarios) {
		
		Map<Integer,Integer> maisRelacionados = new HashMap<Integer, Integer>();
		
		RelacionamentoModel relacionamento = null;
		
		int tamanho = 0;
		
		for(UsuarioModel usuario:usuarios) {
			
			relacionamento = usuario.getRelacionamento();
			
			for (Map.Entry<Integer , ArrayList<UsuarioModel>> mapa : relacionamento.grauUsuario.entrySet()) { 
				tamanho += mapa.getValue().size();
			}
			
			maisRelacionados.put(usuarios.indexOf(usuario), tamanho);

			tamanho = 0;
		}
		
		Map<Integer, Integer> maisRelacionadosOrdenado = maisRelacionados.entrySet().stream().sorted((e1,e2)->
        e2.getValue().compareTo(e1.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		return maisRelacionadosOrdenado;
	}
	
	public static boolean estaRelacionado(UsuarioModel usuarioAtual, UsuarioModel usuarioRelacionar) {
		
		RelacionamentoModel relacionar = null;
		ArrayList<UsuarioModel> usuarios;
		
		relacionar = usuarioAtual.getRelacionamento();
		
		for (Map.Entry<Integer , ArrayList<UsuarioModel>> mapa : relacionar.grauUsuario.entrySet()) { 
			
			usuarios = mapa.getValue();
			
			for(UsuarioModel usuario:usuarios) {
				if(usuario.getProntuario().equals(usuarioRelacionar.getProntuario())) return true;
			}
		}
		
		return false;
		
	}
	
}
