package excecoes;

public class UsuarioPresenteException extends Exception{
	
	public UsuarioPresenteException() {
		super("O usuário já faz parte do grupo informado.");
	}
	
	public UsuarioPresenteException(String menssagem) {
		super(menssagem);
	}

}
