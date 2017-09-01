package dominio;

public class DadosException extends Exception {
	private ErroDeDominio erro;
	
	public DadosException(ErroDeDominio erro) {
		super(erro.toString());
	}

	public ErroDeDominio getErro() {
		return erro;
	}
}
