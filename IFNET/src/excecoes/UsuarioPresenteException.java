package excecoes;

public class UsuarioPresenteException extends Exception{
	
	public UsuarioPresenteException() {
		super("O usu�rio j� faz parte do grupo informado.");
	}
	
	public UsuarioPresenteException(String menssagem) {
		super(menssagem);
	}

}
