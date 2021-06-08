package excecoes;

public class GrupoInexistenteException extends Exception{
	
	public GrupoInexistenteException() {
		super("O código informado não pertence a nenhum grupo existente.");
	}
	
	public GrupoInexistenteException(String menssagem) {
		super(menssagem);
	}

}
