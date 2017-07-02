package cn.com.codemonkey.research.regex.application;

import static cn.com.codemonkey.research.regex.domain.regex.RegexHelper.RIGHT_ARROW;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.ListUtils.isEqualList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.join;

import java.util.ArrayList;
import java.util.Arrays;
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

	/**
	 * Add non terminal symbol for grammar
	 * 
	 * @param nonTerminal
	 */
	public void addNonTerminal(String nonTerminal) {
		if (nonTerminals.contains(nonTerminal)) {
			throw new IllegalArgumentException("Non-terminal must be unique in current CFG.");
		}
		nonTerminals.add(nonTerminal);
	}

	/**
	 * Add terminal symbol for grammar
	 * 
	 * @param terminal
	 */
	public void addTerminal(String terminal) {
		if (terminals.contains(terminal)) {
			throw new IllegalArgumentException("Terminal must be unique in current CFG.");
		}
		terminals.add(terminal);
	}

	/**
	 * Set start symbol for grammar
	 * 
	 * @param startSymbol
	 */
	public void setStartSymbol(String startSymbol) {
		if (!nonTerminals.contains(startSymbol)) {
			throw new IllegalArgumentException("Start symbol must be an existing non-terminal.");
		}
		this.startSymbol = startSymbol;
	}

	/**
	 * Add production for grammar
	 * 
	 * @param leftSymbol
	 * @param rightSymbol
	 * @param otherRightSymbols
	 */
	public void addProduction(String leftSymbol, String rightSymbol, String... otherRightSymbols) {
		List<String> rightSymbols = new ArrayList<>();

		rightSymbols.add(rightSymbol);
		rightSymbols.addAll(Arrays.asList(otherRightSymbols));
		productions.add(new Production(leftSymbol, rightSymbols));
	}

	public String getStartSymbol() {
		return startSymbol;
	}

	/**
	 * Get corresponding non-terminal symbols according to right expression
	 * 
	 * @param rightExpression
	 * @return
	 */
	public List<String> getNonTerminals(List<String> rightExpression) {
		if (rightExpression == null) {
			throw new IllegalArgumentException("Right symbols cannot be null.");
		}

		List<String> nonTerminals = new ArrayList<>();
		for (Production production : productions) {
			if (isEqualList(production.rightSymbols, rightExpression)) {
				nonTerminals.add(production.leftSymbol);
			}
		}

		return nonTerminals;
	}

	/**
	 * Get corresponding right expressions according to non-terminal symbol
	 * 
	 * @param nonTerminal
	 * @return
	 */
	public List<List<String>> getRightExpressions(String nonTerminal) {
		if (isEmpty(nonTerminal)) {
			throw new IllegalArgumentException("Non-terminal cannot be empty.");
		}

		List<List<String>> rightExpressions = new ArrayList<>();
		for (Production production : productions) {
			if (production.leftSymbol.equals(nonTerminal)) {
				rightExpressions.add(production.rightSymbols);
			}
		}

		return rightExpressions;
	}

	public List<Production> getProductions() {
		return productions;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Non-terminals\n");
		for (String nonTerminal : nonTerminals) {
			stringBuilder.append(nonTerminal).append('\n');
		}
		stringBuilder.append('\n');

		stringBuilder.append("Terminals\n");
		for (String terminal : terminals) {
			stringBuilder.append(terminal).append('\n');
		}
		stringBuilder.append('\n');

		stringBuilder.append("Start Symbol - ").append(startSymbol).append('\n').append('\n');

		stringBuilder.append("Productions\n");
		for (String nonTerminal : nonTerminals) {
			List<List<String>> transitions = getRightExpressions(nonTerminal);

			stringBuilder.append(nonTerminal).append(RIGHT_ARROW);
			for (List<String> transition : transitions) {
				stringBuilder.append(join(transition, EMPTY)).append('|');
			}
			stringBuilder.delete(stringBuilder.lastIndexOf("|"), stringBuilder.length()).append("\n");
		}

		return stringBuilder.toString();
	}

	/**
	 * Abstraction of production in CFG. A production is only meaningful within
	 * grammar, therefore it is made an inner class
	 *
	 */
	public class Production {

		private String leftSymbol = null;
		private List<String> rightSymbols = null;

		Production(String leftSymbol, List<String> rightSymbols) {
			if (isEmpty(leftSymbol)) {
				throw new IllegalArgumentException("Left symbol cannot be empty.");
			}
			if (!nonTerminals.contains(leftSymbol)) {
				throw new IllegalArgumentException("Left symbol must be an existing non-terminal.");
			}
			if (isEmpty(rightSymbols)) {
				throw new IllegalArgumentException("Right symbols cannot be empty.");
			}
			for (String rightSymbol : rightSymbols) {
				if (!nonTerminals.contains(rightSymbol) && !terminals.contains(rightSymbol)) {
					throw new IllegalArgumentException("Right symbol must be an existing non-terminal or terminal.");
				}
			}

			this.leftSymbol = leftSymbol;
			this.rightSymbols = rightSymbols;
		}

		public String getLeftSymbol() {
			return leftSymbol;
		}

		public List<String> getRightSymbols() {
			return rightSymbols;
		}

	}
}