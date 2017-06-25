package cn.com.codemonkey.research.regex.domain.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction of Context Free Grammar
 *
 */
public final class CFG {

	private final List<String> nonTerminals = new ArrayList<>();
	private final List<String> terminals = new ArrayList<>();
	private String startSymbol = null;
	private final List<Production> productions = new ArrayList<>();

	public void addNonTerminal(String nonTerminal) {
		if (nonTerminals.contains(nonTerminal)) {
			throw new IllegalArgumentException("Non-termianl must be unique in current CFG.");
		}
		nonTerminals.add(nonTerminal);
	}

	public void addTerminal(String terminal) {
		if (terminals.contains(terminal)) {
			throw new IllegalArgumentException("Termianl must be unique in current CFG.");
		}
		terminals.add(terminal);
	}

	public void setStartSymbol(String startSymbol) {
		if (!nonTerminals.contains(startSymbol)) {
			throw new IllegalArgumentException("Start symbol must be an existing non-terminal.");
		}
		this.startSymbol = startSymbol;
	}

	public void addProduction(String leftSymbol, List<String> rightSymbols) {
		productions.add(new Production(leftSymbol, rightSymbols));
	}

	/**
	 * Abstraction of production in CFG. A production is only meaningful within
	 * grammar, therefore it is made an inner class
	 *
	 */
	class Production {

		private String leftSymbol = null;
		private List<String> rightSymbols = null;

		Production(String leftSymbol, List<String> rightSymbols) {
			if (!nonTerminals.contains(leftSymbol)) {
				throw new IllegalArgumentException("Left symbol must be an existing non-terminal.");
			}
			this.leftSymbol = leftSymbol;
			this.rightSymbols = rightSymbols;
		}

	}
}
