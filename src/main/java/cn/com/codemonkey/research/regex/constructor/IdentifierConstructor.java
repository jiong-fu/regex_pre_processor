package cn.com.codemonkey.research.regex.constructor;

import cn.com.codemonkey.research.regex.domain.automaton.IntStateNFA;
import cn.com.codemonkey.research.regex.domain.automaton.NFA;
import cn.com.codemonkey.research.regex.domain.regex.Regex;

/**
 * Temporary DFA constructor for recognition of identifiers
 */
public final class IdentifierConstructor extends Constructor {

	public IdentifierConstructor() {
		this(null);
	}

	protected IdentifierConstructor(Regex regex) {
		super(regex);
	}

	@Override
	public NFA<Integer, Character> contruct() {
		IntStateNFA<Character> nfa = new IntStateNFA<>();

		nfa.setSymbolicName("id");
		for (char c = '0'; c <= '9'; c++) {
			nfa.addTransition(nfa.getStartState(), c, nfa.getStartState());
		}
		for (char c = 'a'; c <= 'z'; c++) {
			nfa.addTransition(nfa.getStartState(), c, nfa.getStartState());
		}
		for (char c = 'A'; c <= 'Z'; c++) {
			nfa.addTransition(nfa.getStartState(), c, nfa.getStartState());
		}
		nfa.setEndState(nfa.getStartState());

		return nfa;
	}

}
