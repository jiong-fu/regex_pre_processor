package cn.com.codemonkey.research.regex.constructor;

import cn.com.codemonkey.research.regex.domain.automaton.IntStateNFA;
import cn.com.codemonkey.research.regex.domain.automaton.NFA;
import cn.com.codemonkey.research.regex.domain.regex.Regex;

/**
 * Temporary DFA constructor for recognition of numbers
 */
public final class NumConstructor extends Constructor {

	public NumConstructor() {
		this(null);
	}

	protected NumConstructor(Regex regex) {
		super(regex);
	}

	@Override
	public NFA<Integer, Character> contruct() {
		IntStateNFA<Character> nfa = new IntStateNFA<>();

		nfa.setSymbolicName("num");
		for (char c = '0'; c <= '9'; c++) {
			nfa.addTransition(nfa.getStartState(), c, nfa.getStartState());
		}
		nfa.setEndState(nfa.getStartState());

		return nfa;
	}

}
