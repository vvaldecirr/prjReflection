package controle;

import java.lang.annotation.Annotation;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Scanner;

import dominio.Aluno;
import dominio.DadosException;
import dominio.FaixaDeValores;
import dominio.Pessoa;

public class Programa {
	public static void main(String[] args) {
		//
		// Nesta primeira instru��o, estamos declarando uma vari�vel chamada 'p' cujo tipo � 
		// 'ponteiro para um objeto Pessoa'.
		//
		Pessoa p = null;

		try {
			//
			// Temos dois operadores: = e new. Pela preced�ncia, o new ser� executado primeiro
			//
			// O objetivo do new � 'instanciar um novo objeto'
			//
			// Para isto, o operador new realiza duas tarefas:
			// 1) o new aloca mem�ria para o novo objeto (promovendo a inicializa��o default)
			// 2) pede ao objeto rec�m-criado que execute seu m�todo construtor.
			//
			// Ao final, o new devolve/retorna o endere�o de mem�ria onde o objeto foi criado.
			//
			// Depois do new, o operador de atribui��o ser� executado. 
			// p recebe o endere�o de mem�ria onde o objeto Pessoa foi criado; logo,
			// p passa a apontar para este novo objeto
			//
			p = new Pessoa("987654321-01", "Jos� da Silva", 28);
		} catch(DadosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Estamos declarando uma vari�vel chamada 'objClass' cujo tipo � 
		// 'ponteiro para um objeto Class'
		// 
		// Estamos enviando a mensagem getClass() para o objeto apontado por 'p'
		// O m�todo getClass() devolve o endere�o de mem�ria do objeto Class que 
		// descreve a classe Pessoa.
		//
		// objClass recebe o endere�o de mem�ria do objeto Class; logo
		// objClass passa a apontar para este objeto Class
		Class objClass = p.getClass();
		
		// Solicito o nome completo da classe que o objeto Class descreve
		String nomeDaClasse = objClass.getName();
		System.out.println("o objeto Class apontado pela vari�vel 'objClass' descreve a classe " + nomeDaClasse);
		System.out.println();
		
		// Utilizando o objeto Class, listo todos os m�todos declarados da classe Pessoa
		System.out.println("M�todos presentes na classe " + nomeDaClasse);
		System.out.println("-----------------------------------------------");
		for(Method m : objClass.getMethods()) {
			System.out.println(m.toString());
		}
		System.out.println();

		// Utilizando o objeto Class, listo todos os construtores declarados da classe Pessoa
		System.out.println("Construtores presentes na classe " + nomeDaClasse);
		System.out.println("-----------------------------------------------");
		for(Constructor cntr : objClass.getConstructors()) {
			System.out.println(cntr.toString());
		}
		System.out.println();

		// Utilizando o objeto Class, listo todos os atributos declarados da classe Pessoa 
		System.out.println("Atributos presentes na classe " + nomeDaClasse);
		System.out.println("-----------------------------------------------");
		for(Field f : objClass.getDeclaredFields()) {
			System.out.println(f);
			//
			// A seguir, apresentamos como recuperar informa��es sobre anota��es feitas 
			// no c�digo utilizando refletion
			//
			// (1) Listando as anota��es presentes no atributo
			//
			for(Annotation anotacao : f.getAnnotations())
				System.out.println('\t' + anotacao.toString());
			// 
			// (2) Recuperando dados de uma anota��o espec�fica
			// 
			if(f.isAnnotationPresent(FaixaDeValores.class)) {
				FaixaDeValores anotacao = f.getAnnotation(FaixaDeValores.class);
				System.out.println("O atributo " + f.getName() + " apresenta a anota��o Faixa de Valores");
				System.out.println("com valor m�nimo = " + anotacao.minimo() + " e valor m�ximo = " + anotacao.maximo());
			}
		}
		System.out.println();
		
		// Uma outra forma de obter o objeto Class � escrevendo o nome da classe seguido de ".class"
		objClass = Pessoa.class;
		
		// � poss�vel utilizar um objeto Class para criar um objeto da classe que ele
		// descreve, sem utilizar o new. Para isto utilizamos o m�todo newInstance.
		// Este m�todo s� funciona se na classe houver um construtor com assinatura vazia.
		// Da� a raz�o de estar dentro de um bloco de try/catch
		Object objeto = null;
		try {
			// Pede ao objeto Class para instanciar um objeto da classe descrita por ele. 
			// Ser� utilizado o construtor com assinatura vazia.
			objeto = objClass.newInstance();
			System.out.println("O Objeto criado � da classe " + objeto.getClass().getName());		
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// Neste exemplo, vemos como podemos executar m�todos utilizando Refletion
		// A partir do objeto Class, recuperamos os objetos Method para a execu��o. Para isto 
		// devemos indicar o nome do m�todo e os tipos indicados na lista de par�metros		
		try {
			// Recuperando o m�todo setCpf que apresenta 1 par�metro (String) com o m�todo
			// 'getMethod'. Observe que podemos recuperar o objeto Class na forma 
			// 'nome da classe'.class (ex. String.class, Pessoa.class)
			Method metodoSetCpf = objClass.getMethod("setCpf", String.class);
			// solicita que o m�todo seja executado pelo objeto 
			metodoSetCpf.invoke(objeto, "123456789-01");
			
			Method setNome = objClass.getMethod("setNome", String.class);
			setNome.invoke(objeto, "Maria de Souza");
			
			Method setIdade = objClass.getMethod("setIdade", int.class);
			setIdade.invoke(objeto, 48);

			System.out.println("Objeto --->" + objeto);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println();
		
		
		//
		// Trabalhando com Inje��o de Depend�ncia e Invers�o de Controle. Observe que o c�digo s� apresenta
		// Acoplamento com a interface Collection e n�o apresenta acoplamento com nenhuma implementa��o
		// desta interface. S� em tempo de execu��o (Runtime) � que o programa ter� dependencia com a 
		// implementa��o desejada.
		//
		Collection<Pessoa> listaDePessoas = null;
																		
		System.out.println("Entre com o nome da implementa��o de Collection");
		Scanner teclado = new Scanner(System.in);
		String nomeDaCollection = teclado.nextLine();
		
		try {
			// Uma outra forma de obtermos um objeto Class � utilizando o m�todo est�tico forName
			// Neste caso, passamos uma String com o nome da classe a qual desejamos ter o objeto Class.
			Class implementacaoDeCollection = Class.forName(nomeDaCollection);
			listaDePessoas = (Collection<Pessoa>)implementacaoDeCollection.newInstance();
			System.out.println("Estaremos utilizando a implementa��o " + implementacaoDeCollection);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		listaDePessoas.add(p);
		listaDePessoas.add((Pessoa)objeto);
		
		System.out.println(listaDePessoas);
	}
}
