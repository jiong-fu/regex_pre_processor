package cn.com.codemonkey.research.regex.domain.automaton;

/**
 * Non-deterministic Finite Automaton with integer states
 * 
 * @param <A>
 *            The generic type of input characters
 */
public class IntStateNFA<A> extends NFA<Integer, A> {

	private int currentState = 0;

	public IntStateNFA() {
		super(0);
		currentState = startState;
	}

	public Integer getCurrentState() {
		return currentState;
	}

	public void addState() {
		int nextState = currentState + 1;
		super.addState(nextState);
		currentState = nextState;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("IntStateNFA\n");

		for (Transition transition : transitions) {
			stringBuilder.append(transition).append('\n');
		}

		stringBuilder.append("q0 = ").append(startState).append('\n');

		stringBuilder.append("F = [");
		for (int endState : endStates) {
			stringBuilder.append(endState).append(", ");
		}
		stringBuilder.delete(stringBuilder.lastIndexOf(", "), stringBuilder.length()).append("]\n");

		return stringBuilder.toString();
	}

}
