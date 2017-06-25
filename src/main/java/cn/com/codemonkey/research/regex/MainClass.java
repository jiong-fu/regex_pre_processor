package cn.com.codemonkey.research.regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.com.codemonkey.research.regex.constructor.Constructor;
import cn.com.codemonkey.research.regex.domain.automaton.DFA;
import cn.com.codemonkey.research.regex.domain.regex.Regex;

public class MainClass {

	public static void main(String[] args) throws IOException {
		List<Regex> regexes = loadRegexes("rules.txt");
		List<DFA<?, ?>> dfas = new ArrayList<>();

		for (Regex regex : regexes) {
			dfas.add(Constructor.from(regex).contruct());
		}

		for (DFA<?, ?> dfa : dfas) {
			System.out.println(dfa);
		}

		String word = "(for(<){}i=1;i<10;i++)";
		List<String> tokens = tokenize(word, dfas);
		for (String token : tokens) {
			System.out.println(token);
		}

	}

	private static List<String> tokenize(String word, List<DFA<?, ?>> dfas) {
		List<String> tokens = new ArrayList<>();

		for (int i = word.length(); i > 0; i--) {
			String prefix = word.substring(0, i);
			String suffix = word.substring(i, word.length());
			List<Character> prefixChars = new ArrayList<>();

			for (Character c : prefix.toCharArray()) {
				prefixChars.add(c);
			}
			for (DFA dfa : dfas) {
				if (dfa.accept(prefixChars)) {
					tokens.add(prefix);
					tokens.addAll(tokenize(suffix, dfas));
				}
			}
		}

		return tokens;
	}

	private static List<Regex> loadRegexes(String fileName) throws IOException {
		String currentLine = null;
		List<Regex> regexes = new ArrayList<>();
		File file = new File(MainClass.class.getClassLoader().getResource(fileName).getFile());

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			while ((currentLine = bufferedReader.readLine()) != null) {
				if (StringUtils.isNotBlank(currentLine)) {
					regexes.add(Regex.valueOf(currentLine));
				}
			}
			return regexes;
		}
	}

}
