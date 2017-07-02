package cn.com.codemonkey.research.regex.application;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.codemonkey.research.regex.constructor.Constructor;
import cn.com.codemonkey.research.regex.constructor.IdentifierConstructor;
import cn.com.codemonkey.research.regex.constructor.NumConstructor;
import cn.com.codemonkey.research.regex.domain.automaton.NFA;
import cn.com.codemonkey.research.regex.domain.regex.Regex;

public final class Tokenizer {

	List<NFA<Integer, Character>> nfas = new ArrayList<>();

	public Tokenizer() {
		List<Regex> regexes = new ArrayList<>();

		try {
			regexes = loadRegexes("rules.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Regex regex : regexes) {
			nfas.add(Constructor.from(regex).contruct());
		}

		// TODO: remove constructors below when ThompsonConstructor is ready
		nfas.add(new NumConstructor().contruct());
		nfas.add(new IdentifierConstructor().contruct());
	}

	public List<Token> tokenize(String inputString) {
		return tokenize(inputString, nfas);
	}

	private List<Regex> loadRegexes(String fileName) throws IOException {
		String currentLine = null;
		List<Regex> regexes = new ArrayList<>();
		File file = new File(Tokenizer.class.getClassLoader().getResource(fileName).getFile());

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			while ((currentLine = bufferedReader.readLine()) != null) {
				if (isNotBlank(currentLine)) {
					regexes.add(Regex.valueOf(currentLine));
				}
			}
			return regexes;
		}
	}

	private List<Token> tokenize(String word, List<NFA<Integer, Character>> nfas) {
		List<Token> tokens = new ArrayList<>();
		boolean hasFound = false;
		int i = word.length();

		while (!hasFound && i > 0) {
			String prefix = word.substring(0, i);
			String suffix = word.substring(i, word.length());
			List<Character> prefixChars = new ArrayList<>();

			for (Character c : prefix.toCharArray()) {
				prefixChars.add(c);
			}
			for (NFA<Integer, Character> dfa : nfas) {
				if (dfa.accept(prefixChars)) {
					tokens.add(new Token(dfa.getSymbolicName(), prefix));
					if (isNotBlank(suffix)) {
						tokens.addAll(tokenize(suffix, nfas));
					}
					hasFound = true;
					break;
				}
			}
			i--;
		}

		return tokens;
	}

	public class Token {
		private String symbolicName = null;
		private String value = null;

		public Token(String symbolicName, String value) {
			this.symbolicName = symbolicName;
			this.value = value;
		}

		public String getSymbolicName() {
			return symbolicName;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return new StringBuilder().append('[').append(symbolicName).append(", ").append(value).append(']')
					.toString();
		}
	}

}
