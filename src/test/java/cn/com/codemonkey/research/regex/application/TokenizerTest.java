package cn.com.codemonkey.research.regex.application;

import java.util.function.Consumer;

import org.junit.Test;

import cn.com.codemonkey.research.regex.application.Tokenizer.Token;

public class TokenizerTest {

	private static final Tokenizer tokenizer = new Tokenizer();
	private static final Consumer<Token> tokenConsumer = new Consumer<Token>() {

		@Override
		public void accept(Token token) {
			System.out.print(token);
		}

	};

	@Test
	public void testTokenize() {
		tokenizer.tokenize("for(i=0;i<10;i++){print(i);}").forEach(tokenConsumer);
		System.out.println();
		tokenizer.tokenize("while(i>10){i--;print(i);}").forEach(tokenConsumer);
		System.out.println();
		tokenizer.tokenize("if(i+1>10){print(i++);while(i){i--;}}").forEach(tokenConsumer);
		System.out.println();
		tokenizer.tokenize("10*i+1").forEach(tokenConsumer);
		System.out.println();
	}
}
