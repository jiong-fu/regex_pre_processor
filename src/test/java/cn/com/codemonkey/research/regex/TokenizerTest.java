package cn.com.codemonkey.research.regex;

import java.io.IOException;

import org.junit.Test;

public class TokenizerTest {

	private Tokenizer tokenizer = null;

	public TokenizerTest() throws IOException {
		tokenizer = new Tokenizer();
	}

	@Test
	public void testTokenize() {
		tokenizer.tokenize("for(i=0;i<10;i++){print(i);}");
		tokenizer.tokenize("while(i>10){i--;print(i);}");
		tokenizer.tokenize("if(i+1>10){print(i++);while(i){i--;}}");
	}
}
