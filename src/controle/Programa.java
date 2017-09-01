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
		// Nesta primeira instrução, estamos declarando uma variável chamada 'p' cujo tipo é 
		// 'ponteiro para um objeto Pessoa'.
		//
		Pessoa p = null;

		try {
			//
			// Temos dois operadores: = e new. Pela precedência, o new será executado primeiro
			//
			// O objetivo do new é 'instanciar um novo objeto'
			//
			// Para isto, o operador new realiza duas tarefas:
			// 1) o new aloca memória para o novo objeto (promovendo a inicialização default)
			// 2) pede ao objeto recém-criado que execute seu método construtor.
			//
			// Ao final, o new devolve/retorna o endereço de memória onde o objeto foi criado.
			//
			// Depois do new, o operador de atribuição será executado. 
			// p recebe o endereço de memória onde o objeto Pessoa foi criado; logo,
			// p passa a apontar para este novo objeto
			//
			p = new Pessoa("987654321-01", "José da Silva", 28);
		} catch(DadosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Estamos declarando uma variável chamada 'objClass' cujo tipo é 
		// 'ponteiro para um objeto Class'
		// 
		// Estamos enviando a mensagem getClass() para o objeto apontado por 'p'
		// O método getClass() devolve o endereço de memória do objeto Class que 
		// descreve a classe Pessoa.
		//
		// objClass recebe o endereço de memória do objeto Class; logo
		// objClass passa a apontar para este objeto Class
		Class objClass = p.getClass();
		
		// Solicito o nome completo da classe que o objeto Class descreve
		String nomeDaClasse = objClass.getName();
		System.out.println("o objeto Class apontado pela variável 'objClass' descreve a classe " + nomeDaClasse);
		System.out.println();
		
		// Utilizando o objeto Class, listo todos os métodos declarados da classe Pessoa
		System.out.println("Métodos presentes na classe " + nomeDaClasse);
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
			// A seguir, apresentamos como recuperar informações sobre anotações feitas 
			// no código utilizando refletion
			//
			// (1) Listando as anotações presentes no atributo
			//
			for(Annotation anotacao : f.getAnnotations())
				System.out.println('\t' + anotacao.toString());
			// 
			// (2) Recuperando dados de uma anotação específica
			// 
			if(f.isAnnotationPresent(FaixaDeValores.class)) {
				FaixaDeValores anotacao = f.getAnnotation(FaixaDeValores.class);
				System.out.println("O atributo " + f.getName() + " apresenta a anotação Faixa de Valores");
				System.out.println("com valor mínimo = " + anotacao.minimo() + " e valor máximo = " + anotacao.maximo());
			}
		}
		System.out.println();
		
		// Uma outra forma de obter o objeto Class é escrevendo o nome da classe seguido de ".class"
		objClass = Pessoa.class;
		
		// É possível utilizar um objeto Class para criar um objeto da classe que ele
		// descreve, sem utilizar o new. Para isto utilizamos o método newInstance.
		// Este método só funciona se na classe houver um construtor com assinatura vazia.
		// Daí a razão de estar dentro de um bloco de try/catch
		Object objeto = null;
		try {
			// Pede ao objeto Class para instanciar um objeto da classe descrita por ele. 
			// Será utilizado o construtor com assinatura vazia.
			objeto = objClass.newInstance();
			System.out.println("O Objeto criado é da classe " + objeto.getClass().getName());		
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// Neste exemplo, vemos como podemos executar métodos utilizando Refletion
		// A partir do objeto Class, recuperamos os objetos Method para a execução. Para isto 
		// devemos indicar o nome do método e os tipos indicados na lista de parâmetros		
		try {
			// Recuperando o método setCpf que apresenta 1 parâmetro (String) com o método
			// 'getMethod'. Observe que podemos recuperar o objeto Class na forma 
			// 'nome da classe'.class (ex. String.class, Pessoa.class)
			Method metodoSetCpf = objClass.getMethod("setCpf", String.class);
			// solicita que o método seja executado pelo objeto 
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
		// Trabalhando com Injeção de Dependência e Inversão de Controle. Observe que o código só apresenta
		// Acoplamento com a interface Collection e não apresenta acoplamento com nenhuma implementação
		// desta interface. Só em tempo de execução (Runtime) é que o programa terá dependencia com a 
		// implementação desejada.
		//
		Collection<Pessoa> listaDePessoas = null;
																		
		System.out.println("Entre com o nome da implementação de Collection");
		Scanner teclado = new Scanner(System.in);
		String nomeDaCollection = teclado.nextLine();
		
		try {
			// Uma outra forma de obtermos um objeto Class é utilizando o método estático forName
			// Neste caso, passamos uma String com o nome da classe a qual desejamos ter o objeto Class.
			Class implementacaoDeCollection = Class.forName(nomeDaCollection);
			listaDePessoas = (Collection<Pessoa>)implementacaoDeCollection.newInstance();
			System.out.println("Estaremos utilizando a implementação " + implementacaoDeCollection);
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
