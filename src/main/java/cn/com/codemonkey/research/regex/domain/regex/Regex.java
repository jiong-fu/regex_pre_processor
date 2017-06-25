package cn.com.codemonkey.research.regex.domain.regex;

import static cn.com.codemonkey.research.regex.domain.regex.RegexHelper.SPECIAL_CHARS;
import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.remove;

/**
 * Abstraction of regular expressions
 * 
 */
public abstract class Regex {

	protected String inputString = null;

	protected Regex(String inputString) {
		this.inputString = inputString;
	}

	public abstract Object getValue();

	/**
	 * <p>
	 * Return {@link InductiveRegex} or {@link SimpleRegex} instance according
	 * to the input string (factory method)
	 * </p>
	 * <p>
	 * If the input string contains escape characters after removing escape
	 * pairs, then the corresponding regular expression is inductively defined,
	 * therefore {@link InductiveRegex} instance is returned.
	 * </p>
	 * <p>
	 * If the input string doesn't contain escape characters after removing
	 * escape pairs, then the corresponding regular expression is
	 * non-inductively defined, therefore {@link SimpleRegex} instance is
	 * returned.
	 * </p>
	 * 
	 * @param inputString
	 *            input string
	 * @return concrete instance of {@link Regex}
	 */
	public static Regex valueOf(String inputString) {
		return isInductive(inputString) ? new InductiveRegex(inputString) : new SimpleRegex(inputString);
	}

	public static boolean isInductive(String inputString) {
		return containsInductiveChar(removeEscapePairs(inputString));
	}

	private static boolean containsInductiveChar(String inputString) {
		for (int i = 0; i < SPECIAL_CHARS.length; i++) {
			if (contains(inputString, SPECIAL_CHARS[i])) {
				return true;
			}
		}
		return false;
	}

	private static String removeEscapePairs(String input) {
		String output = input;
		for (int i = 0; i < SPECIAL_CHARS.length; i++) {
			String escapeStr = Character.toString('\\') + SPECIAL_CHARS[i];
			output = remove(output, escapeStr);
		}
		return output;
	}
}
