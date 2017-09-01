package dominio;

public class Aluno extends Pessoa {
	
	private String matr;
	
	public Aluno(String cpf, String nome, int idade, String matr) throws DadosException {
		super(cpf, nome, idade);
		this.setMatr(matr);
	}

	public String getMatr() {
		return matr;
	}

	public void setMatr(String matr) throws DadosException {
		Aluno.validarMatr(matr);
		this.matr = matr;
	}

	public static void validarMatr(String matr) throws DadosException {
		if(matr == null || matr.length() == 0)
			throw new DadosException(new ErroDeDominio(1, Aluno.class, "A matrícula não pode ser nula!"));	
		if(matr.length() != 7)
			throw new DadosException(new ErroDeDominio(2, Aluno.class, "A matrícula não está no formato correto!"));	
		for(int i = 0; i < 7; i++)
			if( ! Character.isDigit(matr.charAt(i) ) )
				throw new DadosException(new ErroDeDominio(3, Aluno.class, "O caracter da matrícula na posição " + i + " não é numérico!"));	
	}
	
	@Override
	public String toString() {
		return "Aluno [matr=" + matr + ", getCpf()=" + getCpf()
				+ ", getNome()=" + getNome() + ", getIdade()=" + getIdade()
				+ "]";
	}

}
