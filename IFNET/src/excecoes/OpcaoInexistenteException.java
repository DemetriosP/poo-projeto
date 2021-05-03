package excecoes;

public class OpcaoInexistenteException extends Exception{

	public OpcaoInexistenteException() {
		super("O valor informado n�o corresponde a nenhuma das posi��o dispon�veis");
	}
	
	public OpcaoInexistenteException(String menssagem) {
		super(menssagem);
	}
}
