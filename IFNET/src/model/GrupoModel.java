package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GrupoModel {
	
	private String nome;
	private DisciplinaModel disciplina;
	private ArrayList<UsuarioModel> usuariosGrupo = new ArrayList<UsuarioModel>();
	private ProfessorModel criador;
	private String tipo;
	
	public GrupoModel(String nome, DisciplinaModel disciplina, ProfessorModel usuarioAtual, String tipo) {
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
	
	public DisciplinaModel getDisciplina() {
		return disciplina;
	}
	
	public void setDisciplina(DisciplinaModel disciplina) {
		this.disciplina = disciplina;
	}
	
	public ArrayList<UsuarioModel> getUsuariosGrupo() {
		return usuariosGrupo;
	}

	public void setUsuariosGrupo(ArrayList<UsuarioModel> usuario) {
		this.usuariosGrupo = usuario;
	}
	
	public UsuarioModel getUsuarioGrupo(UsuarioModel usuario) {
		return usuariosGrupo.get(usuariosGrupo.indexOf(usuario));
	}

	public void setUsuarioGrupo(UsuarioModel usuario) {
		this.usuariosGrupo.add(usuario);
	}
	
	public UsuarioModel getCriador() {
		return criador;
	}
	
	public void setCriador(ProfessorModel criador) {
		this.criador = criador;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public static Map<Integer, Integer> consultarGrupoMaisUsuarios(ArrayList<GrupoModel> grupos) {
		
		Map<Integer,Integer> maisUsuarios = new HashMap<Integer, Integer>();
		
		for(GrupoModel grupo:grupos) {
			maisUsuarios.put(grupos.indexOf(grupo), grupo.getUsuariosGrupo().size());
		}
		
		Map<Integer, Integer> maisUsuariosOrdenado = maisUsuarios.entrySet().stream().sorted((e1,e2)->
        e2.getValue().compareTo(e1.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		return maisUsuariosOrdenado;
	}
	
	public static ArrayList<GrupoModel> consultarGpPesquisaPorDisciplina(ArrayList<GrupoModel> grupos, DisciplinaModel disciplina) {
		
		ArrayList<GrupoModel> gruposPesquisados = new ArrayList<GrupoModel>();
		
		for(GrupoModel grupo:grupos) {
			if(grupo.getDisciplina().getNome().contains(disciplina.getNome()) && grupo.getTipo().equals("Pesquisa")) 
				gruposPesquisados.add(grupo);
		}
		
		return gruposPesquisados;
	}
	
	public static ArrayList<GrupoModel> pesquisarGrupos(ArrayList<GrupoModel> grupos, String nome) {
		
		ArrayList<GrupoModel> gruposPesquisados = new ArrayList<GrupoModel>();
		
		for(GrupoModel grupo:grupos) 
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
