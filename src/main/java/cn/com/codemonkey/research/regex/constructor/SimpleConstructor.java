package cn.com.codemonkey.research.regex.constructor;

import cn.com.codemonkey.research.regex.domain.automaton.IntStateNFA;
import cn.com.codemonkey.research.regex.domain.automaton.NFA;
import cn.com.codemonkey.research.regex.domain.regex.SimpleRegex;

/**
 * DFA constructor for non-inductively defined regular expressions. User should
 * not decide which constructor to use, therefore the class and the constructor
 * are made package private.
 */
class SimpleConstructor extends Constructor {

	SimpleConstructor(SimpleRegex regex) {
		super(regex);
	}

	@Override
	public NFA<Integer, Character> contruct() {
		IntStateNFA<Character> nfa = new IntStateNFA<>();

		nfa.setSymbolicName(regex.getValue().toString());
		for (char c : regex.getValue().toString().toCharArray()) {
			int currentState = nfa.getCurrentState();
			nfa.addState();
			int nextState = nfa.getCurrentState();
			nfa.addTransition(currentState, c, nextState);
		}
		nfa.setEndState(nfa.getCurrentState());

		return nfa;
	}

}
