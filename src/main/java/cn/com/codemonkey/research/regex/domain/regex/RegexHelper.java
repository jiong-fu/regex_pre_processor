package cn.com.codemonkey.research.regex.domain.regex;

import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.containsNone;
import static org.apache.commons.lang3.StringUtils.indexOf;

public final class RegexHelper {

	public static final char DELTA = '\u03B4';
	public static final char EPSILON = '\u03B5';
	public static final char LEFT_BRACKET = '[';
	public static final char RIGHT_BRACKET = ']';
	public static final char[] SPECIAL_CHARS = { LEFT_BRACKET, RIGHT_BRACKET, '|', '+', '*' };

	private RegexHelper() {
		throw new UnsupportedOperationException("Cannot instantiate RegexHelper.");
	}

	public static boolean isIncorrectFormat(String input) {
		return (contains(input, LEFT_BRACKET) && containsNone(input, RIGHT_BRACKET))
				|| (contains(input, RIGHT_BRACKET) && containsNone(input, LEFT_BRACKET))
				|| (contains(input, LEFT_BRACKET) && contains(input, RIGHT_BRACKET)
						&& indexOf(input, RIGHT_BRACKET) < indexOf(input, LEFT_BRACKET));
	}

}
