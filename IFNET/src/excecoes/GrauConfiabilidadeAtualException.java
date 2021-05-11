package excecoes;

public class GrauConfiabilidadeAtualException extends Exception{
	
	public GrauConfiabilidadeAtualException() {
		super("O usu�rio j� se encontra no grau de confiabilidade informado");
	}
	
	public GrauConfiabilidadeAtualException(String menssagem) {
		super(menssagem);
	}

}
