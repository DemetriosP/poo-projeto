package excecoes;

public class OpcaoInexistenteException extends Exception{

	public OpcaoInexistenteException() {
		super("O valor informado não corresponde a nenhuma das opções disponíveis");
	}
	
	public OpcaoInexistenteException(String menssagem) {
		super(menssagem);
	}
}
