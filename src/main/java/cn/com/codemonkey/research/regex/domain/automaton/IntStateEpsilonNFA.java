package cn.com.codemonkey.research.regex.domain.automaton;

/**
 * Epsilon Non-deterministic Finite Automaton with Integer states
 *
 * @param <A>
 *            The generic type of input characters
 */
public class IntStateEpsilonNFA<A> extends EpsilonNFA<Integer, A> {

	public IntStateEpsilonNFA() {
		super(0);
	}

}
