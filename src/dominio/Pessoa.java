package dominio;

public class Pessoa {
	private String cpf;
	private String nome;
	@FaixaDeValores(minimo=0,maximo=100)
	private int idade;
	
	public Pessoa() {
	}

	public Pessoa(String cpf, String nome, int idade) throws DadosException {
		super();
		this.setCpf(cpf);
		this.setNome(nome);
		this.setIdade(idade);
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) throws DadosException {
		Pessoa.validarCpf(cpf);
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws DadosException {
		Pessoa.validarNome(nome);
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) throws DadosException {
		Pessoa.validarIdade(idade);
		this.idade = idade;
	}

	public static void validarCpf(String cpf) throws DadosException {
		if(cpf == null || cpf.length() == 0)
			throw new DadosException(new ErroDeDominio(1, Pessoa.class, "O CPF n�o pode ser nulo!"));	
		if(cpf.length() != 12)
			throw new DadosException(new ErroDeDominio(2, Pessoa.class, "O CPF n�o est� no formato correto!"));	
		for(int i = 0; i < 9; i++)
			if( ! Character.isDigit(cpf.charAt(i) ) )
				throw new DadosException(new ErroDeDominio(3, Pessoa.class, "O CPF na posi��o " + i + " n�o � num�rico!"));	
	}
	
	public static void validarNome(String nome) throws DadosException {
		if(nome == null || nome.length() == 0)
			throw new DadosException(new ErroDeDominio(4, Pessoa.class, "O nome n�o pode ser nulo!"));	
	}
		
	public static void validarIdade(int idade) throws DadosException {
		if(idade < 0 || idade > 150)
			throw new DadosException(new ErroDeDominio(5, Pessoa.class, "A idade � inv�lida: " + idade));	
	}
	
	@Override
	public String toString() {
		return "Pessoa [cpf=" + cpf + ", nome=" + nome + ", idade=" + idade
				+ "]";
	}
}
