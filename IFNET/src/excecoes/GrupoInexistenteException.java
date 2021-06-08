package excecoes;

public class GrupoInexistenteException extends Exception{
	
	public GrupoInexistenteException() {
		super("O c�digo informado n�o pertence a nenhum grupo existente.");
	}
	
	public GrupoInexistenteException(String menssagem) {
		super(menssagem);
	}

}
