package cn.com.codemonkey.research.regex.application;

import java.util.ArrayList;
import java.util.List;

import cn.com.codemonkey.research.regex.application.CFG.Production;
import cn.com.codemonkey.research.regex.application.Tokenizer.Token;
import cn.com.codemonkey.research.regex.domain.automaton.IntStateNFA;
import cn.com.codemonkey.research.regex.domain.automaton.NFA;

public final class SyntaxChecker {

	private CFG cfg = null;
	private final List<Token> tokens = new ArrayList<>();
	private IntStateNFA<String> nfa = null;

	public SyntaxChecker(CFG cfg, List<Token> tokens) {
		if (cfg == null) {
			throw new IllegalArgumentException("Input grammar cannot be null.");
		}
		if (tokens == null) {
			throw new IllegalArgumentException("Input tokens cannot be null.");
		}
		this.cfg = cfg;
		tokens.addAll(tokens);

		construct();
		permeate();
	}

	/**
	 * Construct initial NFA
	 */
	private void construct() {
		nfa = new IntStateNFA<>();
		for (Token token : tokens) {
			int currentState = nfa.getCurrentState();
			nfa.addState();
			int nextState = nfa.getCurrentState();
			nfa.addTransition(currentState, token.getSymbolicName(), nextState);
		}
		nfa.setEndState(nfa.getCurrentState());
	}

	/**
	 * Get productions with specific length
	 * 
	 * @param length
	 * @return
	 */
	private List<Production> getProductions(int length) {
		List<Production> productions = new ArrayList<>();

		for (Production production : cfg.getProductions()) {
			if (production.getRightSymbols().size() == length) {
				productions.add(production);
			}
		}

		return productions;
	}

	/**
	 * Recursively permeate NFA
	 */
	private void permeate() {
		for (int step = 0; step < nfa.getCurrentState(); step++) {
			for (Production production : getProductions(step)) {
				for (int current = 0; current < nfa.getCurrentState(); current++) {
					forwardAndCheck(step, current, current, production.getLeftSymbol(), production.getRightSymbols());
				}
			}
		}
	}

	// TODO: the program is not complete implemented because of not enough time.
	// Here is only a draft version of recursion but without testing.
	private void forwardAndCheck(int remainingStep, int start, int current, String leftSymbol,
			List<String> rightSymbols) {
		if (remainingStep > 0) {
			for (NFA<Integer, String>.Transition<Integer, String> transition : nfa.getTransitions(current)) {
				if (transition.getInput().equals(rightSymbols.get(0))) {
					if (rightSymbols.size() > 1) {
						forwardAndCheck(remainingStep--, start, transition.getEndState(), leftSymbol,
								rightSymbols.subList(1, rightSymbols.size()));
					}
				}
			}
		} else {
			nfa.addTransition(start, leftSymbol, current);
		}
	}

}
