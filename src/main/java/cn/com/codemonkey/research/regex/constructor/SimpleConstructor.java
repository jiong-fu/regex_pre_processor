package cn.com.codemonkey.research.regex.constructor;

import cn.com.codemonkey.research.regex.domain.automaton.DFA;
import cn.com.codemonkey.research.regex.domain.automaton.UuidStateDFA;
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
	public DFA<String, Character> contruct() {
		UuidStateDFA<Character> uuidStateDFA = new UuidStateDFA<>();

		for (char c : regex.getValue().toString().toCharArray()) {
			String currentState = uuidStateDFA.getCurrentState();
			uuidStateDFA.addState();
			String nextState = uuidStateDFA.getCurrentState();
			uuidStateDFA.addTransition(currentState, c, nextState);
		}
		uuidStateDFA.setEndState(uuidStateDFA.getCurrentState());

		return uuidStateDFA;
	}

}
