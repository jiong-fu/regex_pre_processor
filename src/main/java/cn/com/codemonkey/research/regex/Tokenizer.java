package cn.com.codemonkey.research.regex;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.codemonkey.research.regex.constructor.Constructor;
import cn.com.codemonkey.research.regex.domain.automaton.DFA;
import cn.com.codemonkey.research.regex.domain.regex.Regex;

public final class Tokenizer {

	List<DFA<String, Character>> dfas = new ArrayList<>();

	public Tokenizer() throws IOException {
		List<Regex> regexes = loadRegexes("rules.txt");

		for (Regex regex : regexes) {
			dfas.add(Constructor.from(regex).contruct());
		}
	}

	public void tokenize(String inputString) {
		List<String> tokens = tokenize(inputString, dfas);
		StringBuilder stringBuilder = new StringBuilder();

		for (String token : tokens) {
			stringBuilder.append(token).append(", ");
		}
		stringBuilder.delete(stringBuilder.lastIndexOf(", "), stringBuilder.length());
		System.out.println(stringBuilder.toString());
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

	private List<String> tokenize(String word, List<DFA<String, Character>> dfas) {
		List<String> tokens = new ArrayList<>();
		boolean hasFound = false;
		int i = word.length();

		while (!hasFound && i > 0) {
			String prefix = word.substring(0, i);
			String suffix = word.substring(i, word.length());
			List<Character> prefixChars = new ArrayList<>();

			for (Character c : prefix.toCharArray()) {
				prefixChars.add(c);
			}
			for (DFA<String, Character> dfa : dfas) {
				if (dfa.accept(prefixChars)) {
					tokens.add(prefix);
					tokens.addAll(tokenize(suffix, dfas));
					hasFound = true;
				}
			}
			i--;
		}

		return tokens;
	}

}
