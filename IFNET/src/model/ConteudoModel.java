package model;

public class ConteudoModel {
	
	private int codigo;
	private String titulo;
	private String tipo;
	private UsuarioModel publicador;

	public ConteudoModel(String titulo, String tipo, UsuarioModel publicador) {
		this.titulo = titulo;
		this.tipo = tipo;
		this.publicador = publicador;
	}
	
	public ConteudoModel(String titulo, String tipo, UsuarioModel publicador, int codigo) {
		this.titulo = titulo;
		this.tipo = tipo;
		this.publicador = publicador;
		this.codigo = codigo;
	}
	
	public String getTipoConteudo() {
		return tipo;
	}

	public void setTipoConteudo(String tipoConteudo) {
		this.tipo = tipoConteudo;
	}

	public UsuarioModel getPublicador() {
		return publicador;
	}

	public void setPublicador(UsuarioModel publicador) {
		this.publicador = publicador;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return this.titulo;
	}

}
