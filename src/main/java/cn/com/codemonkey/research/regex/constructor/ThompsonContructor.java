package cn.com.codemonkey.research.regex.constructor;

import cn.com.codemonkey.research.regex.domain.automaton.DFA;
import cn.com.codemonkey.research.regex.domain.regex.InductiveRegex;

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
	public DFA<Integer, Character> contruct() {
		// TODO:
		// Thompson Construction (Regex -> Epsilon NFA)
		// Eliminate Epsilon States (Epsilon NFA -> DFA)
		return null;
	}

}
