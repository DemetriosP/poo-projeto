package ifnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Grupo {
	
	private String nome;
	private Disciplina disciplina;
	private ArrayList<Usuario> usuariosGrupo = new ArrayList<Usuario>();
	private Professor criador;
	private String tipo;
	
	public Grupo(String nome, Disciplina disciplina, Professor usuarioAtual, String tipo) {
		this.nome = nome;
		this.disciplina = disciplina;
		this.criador = usuarioAtual;
		this.tipo = tipo;
		this.usuariosGrupo.add(usuarioAtual);
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Disciplina getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	
	public ArrayList<Usuario> getUsuariosGrupo() {
		return usuariosGrupo;
	}

	public void setUsuariosGrupo(ArrayList<Usuario> usuario) {
		this.usuariosGrupo = usuario;
	}
	
	public Usuario getCriador() {
		return criador;
	}
	
	public void setCriador(Professor criador) {
		this.criador = criador;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public static Map<Integer, Integer> consultarGrupoMaisUsuarios(ArrayList<Grupo> grupos) {
		
		Map<Integer,Integer> maisUsuarios = new HashMap<Integer, Integer>();
		
		for(Grupo grupo:grupos) {
			maisUsuarios.put(grupos.indexOf(grupo), grupo.getUsuariosGrupo().size());
		}
		
		Map<Integer, Integer> maisUsuariosOrdenado = maisUsuarios.entrySet().stream().sorted((e1,e2)->
        e2.getValue().compareTo(e1.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		return maisUsuariosOrdenado;
	}
	
	public static ArrayList<Grupo> consultarGpPesquisaPorDisciplina(ArrayList<Grupo> grupos, Disciplina disciplina) {
		
		ArrayList<Grupo> gruposPesquisados = new ArrayList<Grupo>();
		
		for(Grupo grupo:grupos) {
			if(grupo.getDisciplina().getNome().contains(disciplina.getNome()) && grupo.getTipo().equals("Pesquisa")) 
				gruposPesquisados.add(grupo);
		}
		
		return gruposPesquisados;
	}
	
	public static ArrayList<Grupo> pesquisarGrupos(ArrayList<Grupo> grupos, String nome) {
		
		ArrayList<Grupo> gruposPesquisados = new ArrayList<Grupo>();
		
		for(Grupo grupo:grupos) 
			if(grupo.getNome().toLowerCase().contains(nome.toLowerCase())) 
				gruposPesquisados.add(grupo);
		
		return gruposPesquisados;
		
	}

	@Override
	public String toString() {
		return "Nome: " + this.nome + 
				"\nDisciplina: " + this.disciplina.getNome() + 
				"\nQuantidade de Usuários: " + this.usuariosGrupo.size() + 
				"\nCriador: " + this.criador.getNome() + 
				"\nTipo: " + this.tipo;
	}

}
