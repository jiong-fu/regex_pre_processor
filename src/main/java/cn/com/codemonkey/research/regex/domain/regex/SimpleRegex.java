package cn.com.codemonkey.research.regex.domain.regex;

import static cn.com.codemonkey.research.regex.domain.regex.RegexHelper.SPECIAL_CHARS;
import static org.apache.commons.lang3.StringUtils.replace;

/**
 * Abstraction of non-inductively defined regular expressions. User should not
 * decide which regular expression to instantiate, therefore the constructor is
 * made package private.
 *
 */
public final class SimpleRegex extends Regex {

	SimpleRegex(String inputString) {
		super(inputString);
	}

	@Override
	public String getValue() {
		return removeEscapeChars(inputString);
	}

	private String removeEscapeChars(String input) {
		String output = input;
		for (int i = 0; i < SPECIAL_CHARS.length; i++) {
			String escapeStr = Character.toString('\\') + SPECIAL_CHARS[i];
			output = replace(output, escapeStr, Character.toString(SPECIAL_CHARS[i]));
		}
		return output;
	}

}
