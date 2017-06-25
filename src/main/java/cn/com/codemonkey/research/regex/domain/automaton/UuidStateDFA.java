package cn.com.codemonkey.research.regex.domain.automaton;

import java.util.UUID;

/**
 * <p>
 * Deterministic Finite Automaton with UUID states
 * </p>
 * <p>
 * UUID states are used to avoid potential naming conflicts when merging several
 * DFAs
 * </p>
 *
 * @param <A>
 *            The generic type of input characters
 */
public class UuidStateDFA<A> extends DFA<String, A> {

	private String currentState = null;

	public UuidStateDFA() {
		super(UUID.randomUUID().toString());
		currentState = startState;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void addState() {
		String nextState = UUID.randomUUID().toString();
		super.addState(nextState);
		currentState = nextState;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("IntStateDFA\n");

		for (Transition transition : transitions) {
			stringBuilder.append(transition).append('\n');
		}

		stringBuilder.append("q0 = ").append(startState).append('\n');

		stringBuilder.append("F = [");
		for (String endState : endStates) {
			stringBuilder.append(endState).append(", ");
		}
		stringBuilder.delete(stringBuilder.lastIndexOf(", "), stringBuilder.length()).append("]\n");

		return stringBuilder.toString();
	}

}
