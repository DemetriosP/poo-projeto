package excecoes;

public class UsuarioCadastradoException extends Exception{
	
	public UsuarioCadastradoException() {
		super("Esse usu�rio j� est� cadastrado no sistema");
	}
	
	public UsuarioCadastradoException(String menssagem) {
		super(menssagem);
	}

}
