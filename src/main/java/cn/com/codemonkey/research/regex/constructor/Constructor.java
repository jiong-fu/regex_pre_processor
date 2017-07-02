package cn.com.codemonkey.research.regex.constructor;

import cn.com.codemonkey.research.regex.domain.automaton.NFA;
import cn.com.codemonkey.research.regex.domain.regex.InductiveRegex;
import cn.com.codemonkey.research.regex.domain.regex.Regex;
import cn.com.codemonkey.research.regex.domain.regex.SimpleRegex;

public abstract class Constructor {

	protected Regex regex = null;

	protected Constructor(Regex regex) {
		this.regex = regex;
	}

	/**
	 * <p>
	 * Return {@link ThompsonConstructor} or {@link SimpleConstructor} instance
	 * according to the regular expression (factory method)
	 * </p>
	 * <p>
	 * If the regular expression is of type {@link InductiveRegex}, then
	 * {@link ThompsonContructor} instance is returned.
	 * </p>
	 * <p>
	 * If the regular expression is of type {@link SimpleRegex}, then
	 * {@link SimpleConstructor} instance is returned.
	 * </p>
	 * 
	 * @param regex
	 *            regular expression
	 * @return concrete instance of {@link Constructor}
	 */
	public static Constructor from(Regex regex) {
		if (regex instanceof InductiveRegex) {
			return new ThompsonContructor((InductiveRegex) regex);
		} else if (regex instanceof SimpleRegex) {
			return new SimpleConstructor((SimpleRegex) regex);
		}
		throw new UnsupportedOperationException("Unsupported Regular expression type.");
	}

	/**
	 * Construct Non-deterministic Finite Automaton with given regular
	 * expression
	 * 
	 * @return
	 */
	public abstract NFA<Integer, Character> contruct();

}
