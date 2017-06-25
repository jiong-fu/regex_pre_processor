package cn.com.codemonkey.research.regex.domain.automaton;

/**
 * Deterministic Finite Automaton is a special case of Non-deterministic Finite
 * Automaton, therefore it is a subclass of {@link NFA}
 *
 * @param <S>
 *            The generic type of state
 * @param <A>
 *            The generic type of input characters
 */
public class DFA<S, A> extends NFA<S, A> {

	public DFA(S startState) {
		super(startState);
	}

}
