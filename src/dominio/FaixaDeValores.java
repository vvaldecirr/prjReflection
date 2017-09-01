package dominio;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Estamos criando uma anotação a ser aplicada a atributos. 
 * @author Unigranrio
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FaixaDeValores {
	int minimo();
	int maximo();
}
