package cn.com.codemonkey.research.regex.constructor;

import static cn.com.codemonkey.research.regex.domain.regex.RegexHelper.EPSILON;

import java.util.ArrayList;
import java.util.List;

import cn.com.codemonkey.research.regex.domain.automaton.DFA;
import cn.com.codemonkey.research.regex.domain.automaton.NFA;
import cn.com.codemonkey.research.regex.domain.regex.InductiveRegex;
import cn.com.codemonkey.research.regex.domain.regex.InductiveRegex.Occurence;
import cn.com.codemonkey.research.regex.domain.regex.Regex;

/**
 * DFA constructor for inductively defined regular expressions. User should not
 * decide which constructor to use, therefore the class and the constructor are
 * made package private.
 *
 */
class ThompsonContructor extends Constructor {

	ThompsonContructor(InductiveRegex regex) {
		super(regex);
	}

	@Override
	public DFA<String, Character> contruct() {
		// TODO:
		// Thompson Construction (Regex -> Epsilon NFA)
		InductiveRegex inductiveRegex = (InductiveRegex) regex;
		List<NFA<String, Character>> dfas = new ArrayList<>();

		for (Regex childRegex : inductiveRegex.getValue()) {
			dfas.add(Constructor.from(childRegex).contruct());
		}

		NFA<String, Character> mergedNFA = null;
		if (!inductiveRegex.getOccurence().equals(Occurence.SELECTIVE)) {
			mergedNFA = new NFA<>(dfas.get(0), dfas.get(1), EPSILON);
			for (int i = 2; i <= dfas.size(); i++) {
				mergedNFA = new NFA<>(mergedNFA, dfas.get(i), EPSILON);
			}
			System.out.println(mergedNFA);
		}

		// Eliminate Epsilon States (Epsilon NFA -> DFA)

		return null;
	}

}
