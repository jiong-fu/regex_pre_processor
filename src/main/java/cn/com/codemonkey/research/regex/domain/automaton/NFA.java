package cn.com.codemonkey.research.regex.domain.automaton;

import static cn.com.codemonkey.research.regex.domain.regex.RegexHelper.DELTA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstraction of Non-deterministic Finite Automaton
 *
 * @param <S>
 *            The generic type of state
 * @param <A>
 *            The generic type of input characters
 */
public class NFA<S, A> {

	protected S startState = null;
	protected Set<S> endStates = new HashSet<>();
	protected final Set<S> states = new HashSet<>();
	protected final List<Transition> transitions = new ArrayList<>();

	public NFA(S startState) {
		if (startState == null) {
			throw new IllegalArgumentException("Start state cannot be null.");
		}
		this.startState = startState;
		states.add(startState);
	}

	/**
	 * Set end state to NFA
	 * 
	 * @param endState
	 */
	public void setEndState(S endState) {
		if (!states.contains(endState)) {
			throw new IllegalArgumentException("End state must be an existing state in current NFA.");
		}
		endStates.add(endState);
	}

	/**
	 * Add single state to NFA
	 * 
	 * @param state
	 */
	public void addState(S state) {
		if (states.contains(state)) {
			throw new IllegalArgumentException("State must be unique in current NFA.");
		}
		states.add(state);
	}

	/**
	 * Add single transition to NFA
	 * 
	 * @param transStart
	 * @param input
	 * @param transEnd
	 */
	public void addTransition(S transStart, A input, S transEnd) {
		if (!states.contains(transStart)) {
			throw new IllegalArgumentException("Transition start isn't contained in the automat.");
		}
		if (!states.contains(transEnd)) {
			throw new IllegalArgumentException("Transition end isn't contained in the automat.");
		}
		transitions.add(new Transition(transStart, input, transEnd));
	}

	/**
	 * Check if the word is accepted by NFA
	 * 
	 * @param words
	 * @return
	 */
	public boolean accept(List<A> words) {
		Set<S> resultStates = simulate(startState, words);
		for (S resultState : resultStates) {
			for (S endState : endStates) {
				if (resultState.equals(endState)) {
					return true;
				}
			}
		}
		return false;
	}

	protected Set<S> simulate(S startState, List<A> words) {
		if (startState == null) {
			throw new IllegalArgumentException("Start state cannot be null.");
		}
		if (words == null) {
			throw new IllegalArgumentException("Input words cannot be null.");
		}

		Set<S> resultStates = new HashSet<>();

		if (words.isEmpty()) {
			// when there is no word left, return start state directly
			resultStates.add(startState);
		} else {
			// when there are words left, get transitions
			// according to start state and the first word in the list
			List<Transition> transitions = getTransitions(startState, words.get(0));
			for (Transition transition : transitions) {
				// simulate recursively with result state as start state
				// and remaining words
				List<A> remainingWords = words.subList(1, words.size());
				resultStates.addAll(simulate(transition.transEnd, remainingWords));
			}
		}

		return resultStates;
	}

	protected List<Transition> getTransitions(S startState, A input) {
		if (transitions == null) {
			throw new IllegalArgumentException("Transition list hasn't been initialized.");
		}

		List<Transition> resultTransitions = new ArrayList<>();
		for (Transition transition : transitions) {
			if (transition.transStart.equals(startState) && transition.input.equals(input)) {
				resultTransitions.add(transition);
			}
		}

		return resultTransitions;
	}

	/**
	 * Abstraction of transition in NFA. A transition is only meaningful within
	 * automaton, therefore it is made an inner class
	 *
	 */
	class Transition {

		private S transStart = null, transEnd = null;
		private A input = null;

		Transition(S startState, A input, S endState) {
			this.transStart = startState;
			this.input = input;
			this.transEnd = endState;
		}

		@Override
		public String toString() {
			return new StringBuilder().append(DELTA).append("(").append(transStart).append(", ").append(input)
					.append(") = ").append(transEnd).toString();
		}

	}
}
