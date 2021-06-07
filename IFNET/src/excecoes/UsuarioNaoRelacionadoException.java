package excecoes;

public class UsuarioNaoRelacionadoException extends Exception{
	
	public UsuarioNaoRelacionadoException() {
		super("Você não tem relacionamento com o usuário informado.");
	}
	
	public UsuarioNaoRelacionadoException(String menssagem) {
		super(menssagem);
	}

}