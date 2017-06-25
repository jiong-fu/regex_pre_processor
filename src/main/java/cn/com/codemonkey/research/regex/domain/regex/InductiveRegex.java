package cn.com.codemonkey.research.regex.domain.regex;

/**
 * 
 * Abstraction of inductively defined regular expressions. User should not
 * decide which regular expression to instantiate, therefore the constructor is
 * made package private.
 *
 */
public final class InductiveRegex extends Regex {

	protected InductiveRegex(String inputString) {
		super(inputString);
	}

	@Override
	public Object getValue() {
		// TODO: Prepare tree structure for ThompsonConstructor
		return null;
	}

}
