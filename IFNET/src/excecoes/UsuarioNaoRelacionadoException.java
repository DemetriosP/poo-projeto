package excecoes;

public class UsuarioNaoRelacionadoException extends Exception{
	
	public UsuarioNaoRelacionadoException() {
		super("Voc� n�o tem relacionamento com o usu�rio informado.");
	}
	
	public UsuarioNaoRelacionadoException(String menssagem) {
		super(menssagem);
	}

}