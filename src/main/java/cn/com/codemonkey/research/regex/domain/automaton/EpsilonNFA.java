package cn.com.codemonkey.research.regex.domain.automaton;

/**
 * Epsilon Non-deterministic Finite Automaton is a special case of
 * Non-deterministic Automaton, therefore it is a subclass of {@link NFA}
 *
 * @param <S>
 *            The generic type of state
 * @param <A>
 *            The generic type of input characters
 */
public class EpsilonNFA<S, A> extends NFA<S, A> {

	public EpsilonNFA(S startState) {
		super(startState);
	}

}
